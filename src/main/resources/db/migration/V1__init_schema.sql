-- ============================================================
-- V1: University Management System — Schema
-- Migrated from legacy natural-key schema to surrogate PKs.
-- Key change: IsTaking + HasTaken merged into enrollments
-- with a status enum (ENROLLED, COMPLETED, DROPPED).
-- ============================================================

-- ----------------------------
-- Departments
-- ----------------------------
CREATE TABLE departments (
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    name       VARCHAR(255) NOT NULL,
    campus     VARCHAR(255) NOT NULL,
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_departments_name (name)
) ENGINE=InnoDB;

-- ----------------------------
-- Students
-- ----------------------------
CREATE TABLE students (
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_students_email (email)
) ENGINE=InnoDB;

-- ----------------------------
-- Courses
-- ----------------------------
CREATE TABLE courses (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    name            VARCHAR(255) NOT NULL,
    credits         INT          NOT NULL DEFAULT 3,
    max_enrollment  INT          NOT NULL DEFAULT 40,
    department_id   BIGINT       NOT NULL,
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_courses_name (name),
    CONSTRAINT fk_courses_department FOREIGN KEY (department_id) REFERENCES departments(id)
) ENGINE=InnoDB;

-- ----------------------------
-- Enrollments  (replaces IsTaking + HasTaken)
-- ----------------------------
CREATE TABLE enrollments (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    student_id  BIGINT       NOT NULL,
    course_id   BIGINT       NOT NULL,
    status      ENUM('ENROLLED','COMPLETED','DROPPED') NOT NULL DEFAULT 'ENROLLED',
    grade       VARCHAR(2)   NULL,
    enrolled_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_enrollments_student FOREIGN KEY (student_id) REFERENCES students(id),
    CONSTRAINT fk_enrollments_course  FOREIGN KEY (course_id)  REFERENCES courses(id),
    UNIQUE KEY uk_enrollments_active (student_id, course_id, status)
) ENGINE=InnoDB;

-- ----------------------------
-- Student Majors (join table)
-- ----------------------------
CREATE TABLE student_majors (
    student_id    BIGINT NOT NULL,
    department_id BIGINT NOT NULL,
    PRIMARY KEY (student_id, department_id),
    CONSTRAINT fk_student_majors_student    FOREIGN KEY (student_id)    REFERENCES students(id),
    CONSTRAINT fk_student_majors_department FOREIGN KEY (department_id) REFERENCES departments(id)
) ENGINE=InnoDB;

-- ----------------------------
-- Student Minors (join table)
-- ----------------------------
CREATE TABLE student_minors (
    student_id    BIGINT NOT NULL,
    department_id BIGINT NOT NULL,
    PRIMARY KEY (student_id, department_id),
    CONSTRAINT fk_student_minors_student    FOREIGN KEY (student_id)    REFERENCES students(id),
    CONSTRAINT fk_student_minors_department FOREIGN KEY (department_id) REFERENCES departments(id)
) ENGINE=InnoDB;
