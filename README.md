# University Management System

A Spring Boot REST API for managing university departments, courses, students, and course enrollments. The project uses Spring Data JPA with a MySQL database, Flyway for schema migrations and database version control, OpenAPI/Swagger for interactive documentation, and Docker for containerized deployment. This project is a modernization and continuation of an earlier standalone JDBC command-line application located in the legacy directory.

---

## Architecture and Directory Structure

The project follows a standard layered architecture to maintain separation of concerns, testability, and maintainability.

```
src/main/java/com/mattmccaughan/university/
├── config/
├── controller/
├── dto/
├── entity/
├── exception/
├── mapper/
├── repository/
├── service/
└── UniversityApplication.java
```

### 1. `config/` (Configuration Layer)
Contains Spring `@Configuration` classes that customize and extend the application context.
* `AuditConfig.java`: Enables Spring Data JPA Auditing via `@EnableJpaAuditing`. Automatically populates `@CreatedDate` and `@LastModifiedDate` fields on persistent entities.
* `OpenApiConfig.java`: Configures the OpenAPI 3.0 specification metadata (API title, version, description, and contact info) for Swagger UI documentation.
* Centralizes framework-level bean definitions and infrastructure settings instead of scattering configuration across business logic.

### 2. `controller/` (Presentation / Web Layer)
Exposes RESTful HTTP endpoints for external clients.
* `CourseController.java`: Routes for course CRUD operations, pagination, and course rosters (`/api/courses`).
* `DepartmentController.java`: Routes for department CRUD and querying students affiliated with a department (`/api/departments`).
* `EnrollmentController.java`: Routes for retrieving enrollment records and patching enrollment status or grades (`/api/enrollments`).
* `StudentController.java`: Routes for student management, GPA calculation, transcripts, and course enrollment (`/api/students`).
* Acts as the entry point for HTTP requests. It handles routing, query parameters, path variables, request validation triggers (`@Valid`), and HTTP response status codes without containing database or business rules.

### 3. `dto/` (Data Transfer Object Layer)
Defines plain Java objects used to transport data across the API boundary.
* Request DTOs: `StudentCreateRequest.java`, `StudentUpdateRequest.java`, `CourseCreateRequest.java`, `DepartmentCreateRequest.java`, `EnrollmentRequest.java`, `GradeUpdateRequest.java`. These contain Jakarta validation annotations (`@NotBlank`, `@NotNull`, `@Min`, `@Email`).
* Response DTOs: `StudentDto.java`, `CourseDto.java`, `DepartmentDto.java`, `EnrollmentDto.java`, `GpaDto.java`, `TranscriptDto.java`, `ErrorResponse.java`.
* Decouples internal database entity structures from external API contracts. This prevents over-fetching, avoids exposing sensitive database internals, eliminates circular reference issues during JSON serialization, and provides request validation boundaries.

### 4. `entity/` (Persistence / Domain Layer)
Contains JPA entities that map directly to relational database tables.
* `Student.java`: Maps to the `students` table. Defines many-to-many relationships for majors and minors (`student_majors`, `student_minors`) and a one-to-many relationship with enrollments.
* `Department.java`: Maps to the `departments` table. Holds department name, campus, and one-to-many relationship with courses.
* `Course.java`: Maps to the `courses` table. Stores name, credits, maximum capacity, and department foreign key.
* `Enrollment.java`: Maps to the `enrollments` table. Represents the association between a student and a course, tracking enrollment lifecycle status and final grade.
* `EnrollmentStatus.java`: Enum representing the lifecycle states: `ENROLLED`, `COMPLETED`, `DROPPED`.
* Defines the core domain model and object-relational mappings (ORM) for Hibernate and the underlying MySQL schema.

### 5. `exception/` (Error Handling Layer)
Contains domain-specific runtime exceptions and centralized exception interception.
* `ResourceNotFoundException.java`: Thrown when an entity lookup fails (maps to HTTP 404).
* `CourseAtCapacityException.java`: Thrown when trying to enroll in a full course (maps to HTTP 409).
* `DuplicateEnrollmentException.java`: Thrown when an active enrollment already exists for a student and course (maps to HTTP 409).
* `GlobalExceptionHandler.java`: Annotated with `@RestControllerAdvice`. Catches exceptions thrown across all controllers and formats them into uniform `ErrorResponse` JSON objects.
* Prevents unhandled server stack traces from leaking to clients and provides standardized, predictable error structures for API consumers.

### 6. `mapper/` (Object Mapping Layer)
Converts JPA entity instances into response DTOs.
* `StudentMapper.java`: Maps `Student` entities to `StudentDto`, resolving major and minor department name lists.
* `CourseMapper.java`: Maps `Course` entities to `CourseDto`, appending dynamic active enrollment counts.
* `DepartmentMapper.java`: Maps `Department` entities to `DepartmentDto`, computing total course counts.
* `EnrollmentMapper.java`: Maps `Enrollment` entities to `EnrollmentDto`, flattening student and course relations.
* Separates conversion logic from both business services and database entities, adhering to the Single Responsibility Principle.

### 7. `repository/` (Data Access Layer)
Spring Data JPA repository interfaces providing database interaction.
* `StudentRepository.java`: Custom JPQL queries for major/minor lookups, case-insensitive name search, and email checks.
* `CourseRepository.java`: Department-based lookups and pagination.
* `DepartmentRepository.java`: Case-insensitive lookups by department name.
* `EnrollmentRepository.java`: Student/course status filters and seat counting queries.
* Eliminates boilerplate JDBC and SQL code by generating database query implementations automatically at runtime based on interface method declarations.

### 8. `service/` (Business Logic Layer)
Implements application workflows, transaction boundaries, and business rules.
* `StudentService.java`: Student profile management, GPA computation based on credit weightings, and academic transcript generation.
* `CourseService.java`: Course creation, capacity resolution, and active roster lookups.
* `DepartmentService.java`: Department management and student roster lookups by department.
* `EnrollmentService.java`: Course registration rules, duplicate checks, seat limits, and grade recording.
* Holds core business calculations and guarantees data consistency by using `@Transactional` boundaries across multiple repository operations.

---

## Supporting Directories and Files

* `legacy/`: Contains the original standalone Java JDBC application (`DataBase.java`) and Python data generator (`DataGen.py`).
* `data-generator/`: Contains `generate_seed_data.py`, an updated Python script to generate seed SQL or populate MySQL directly.
* `src/main/resources/db/migration/`: Flyway database migration scripts (`V1__init_schema.sql` and `V2__seed_data.sql`) ensuring deterministic schema creation on startup.
* `docker-compose.yml` & `Dockerfile`: Multi-container setup for the Spring Boot application and MySQL database.

---

## Component Interaction and Request Flow

The following diagram illustrates how incoming client requests travel through the application layers:

```
[ Client Request ]
       |
       v
+-------------------------------------------------------------------+
| Controller Layer (e.g. StudentController)                         |
| - Receives HTTP Request (GET, POST, PUT, DELETE, PATCH)           |
| - Validates input using DTO annotations (@Valid)                  |
+-------------------------------------------------------------------+
       |
       v  (Passes validated DTO / ID parameters)
+-------------------------------------------------------------------+
| Service Layer (e.g. EnrollmentService, StudentService)            |
| - Executes business logic (checks capacity, GPA math, duplicates) |
| - Manages transaction boundaries (@Transactional)                 |
+-------------------------------------------------------------------+
       |                                   |
       | (Queries / Persists)              | (Transforms Entity to DTO)
       v                                   v
+-----------------------------+     +-------------------------------+
| Repository Layer            |     | Mapper Layer                  |
| (e.g. EnrollmentRepository) |     | (e.g. EnrollmentMapper)       |
+-----------------------------+     +-------------------------------+
       |                                   |
       v                                   |
+-----------------------------+            |
| Database (MySQL)            |            |
| - Tables, constraints, data |            |
+-----------------------------+            |
       |                                   |
       +-------> Returns Entity ---------->+
                                           |
                                           v  (Returns Response DTO)
+-------------------------------------------------------------------+
| Global Exception Handler (@RestControllerAdvice)                  |
| - Intercepts any thrown exceptions (e.g. ResourceNotFoundException)|
| - Serializes error into standardized ErrorResponse JSON           |
+-------------------------------------------------------------------+
       |
       v
[ HTTP JSON Response (Status Code + Payload) ]
```

### Step-by-Step Flow Example: Course Enrollment (`POST /api/students/{id}/enrollments`)

1. **Client Request**: Client sends a `POST` request with JSON payload `{"courseId": 3}` to `/api/students/1/enrollments`.
2. **Controller**: `StudentController` receives the request, validates `EnrollmentRequest`, and calls `EnrollmentService.enroll(1, request)`.
3. **Service Validation**: `EnrollmentService` performs domain checks:
   - Fetches `Student` from `StudentRepository` (throws `ResourceNotFoundException` if missing).
   - Fetches `Course` from `CourseRepository` (throws `ResourceNotFoundException` if missing).
   - Checks if the student is already actively enrolled (throws `DuplicateEnrollmentException` if true).
   - Checks if active seats >= `maxEnrollment` (throws `CourseAtCapacityException` if full).
4. **Persistence**: An `Enrollment` entity with status `ENROLLED` is created and saved via `EnrollmentRepository`.
5. **Mapping**: `EnrollmentMapper` converts the saved `Enrollment` entity into an `EnrollmentDto`.
6. **Response**: `StudentController` returns the `EnrollmentDto` with HTTP status `201 Created`.
7. **Error Flow**: If any step fails (for example, course at capacity), the thrown exception is intercepted by `GlobalExceptionHandler`, which formats a `409 Conflict` JSON payload with timestamp, status code, error message, and request path.

---

## GPA Calculation Logic

GPA is calculated based on completed course enrollments using a standard 4.0 scale:

| Letter Grade | Grade Points |
| :--- | :--- |
| A+, A | 4.0 |
| A- | 3.7 |
| B+ | 3.3 |
| B | 3.0 |
| B- | 2.7 |
| C+ | 2.3 |
| C | 2.0 |
| C- | 1.7 |
| D+ | 1.3 |
| D | 1.0 |
| D- | 0.7 |
| F | 0.0 |

`GPA = Total Grade Points / Total Attempted Credits` where `Grade Points = Course Credits * Grade Point Value`.

---

## Running the Application

### Prerequisites
* Java 21 SDK
* Maven 3.9+
* MySQL 8.0+ or Docker Desktop

### Option 1: Running with Docker Compose (Recommended)
This starts both the MySQL database and the Spring Boot application container:

```bash
docker-compose up --build
```

The application will be accessible at `http://localhost:8080`.
Swagger UI documentation will be available at `http://localhost:8080/swagger-ui/index.html`.

### Option 2: Running Locally with Maven
1. Ensure a MySQL database is running on `localhost:3306` with database name `university`, user `root`, and password `password` (or update `src/main/resources/application.yml`).
2. Run the application:

```bash
mvn spring-boot:run
```

---

## API Endpoints Summary

### Students (`/api/students`)
* `GET /api/students` : List all students (paginated).
* `GET /api/students/{id}` : Get student profile by ID.
* `POST /api/students` : Create a new student.
* `PUT /api/students/{id}` : Update an existing student.
* `DELETE /api/students/{id}` : Delete a student record.
* `GET /api/students/{id}/gpa` : Retrieve computed student GPA and credit statistics.
* `GET /api/students/{id}/transcript` : Retrieve full transcript with course history and GPA.
* `POST /api/students/{id}/enrollments` : Enroll student in a course.
* `GET /api/students/{id}/enrollments` : List student enrollments (supports optional `?status=` filter).

### Courses (`/api/courses`)
* `GET /api/courses` : List all courses (paginated).
* `GET /api/courses/{id}` : Get course details by ID.
* `POST /api/courses` : Create a new course.
* `PUT /api/courses/{id}` : Update an existing course.
* `DELETE /api/courses/{id}` : Delete a course.
* `GET /api/courses/{id}/roster` : Get list of actively enrolled students for a course.

### Departments (`/api/departments`)
* `GET /api/departments` : List all departments (paginated).
* `GET /api/departments/{id}` : Get department details by ID.
* `POST /api/departments` : Create a new department.
* `PUT /api/departments/{id}` : Update an existing department.
* `DELETE /api/departments/{id}` : Delete a department.
* `GET /api/departments/{name}/students` : List students majoring or minoring in the department.

### Enrollments (`/api/enrollments`)
* `GET /api/enrollments/{id}` : Get enrollment details by ID.
* `PATCH /api/enrollments/{id}` : Update enrollment status or assign a letter grade.
