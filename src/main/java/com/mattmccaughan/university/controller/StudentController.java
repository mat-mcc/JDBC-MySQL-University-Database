// StudentController.java
// REST Controller exposing endpoints for student profiles and academic records.
// Maps endpoints under /api/students for student CRUD, GPA calculations, transcripts, and course enrollments.
package com.mattmccaughan.university.controller;

import com.mattmccaughan.university.dto.*;
import com.mattmccaughan.university.entity.EnrollmentStatus;
import com.mattmccaughan.university.service.EnrollmentService;
import com.mattmccaughan.university.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final EnrollmentService enrollmentService;

    // GET /api/students - Retrieves paginated list of all students.
    @GetMapping
    public Page<StudentDto> getAllStudents(@ParameterObject Pageable pageable) {
        return studentService.getAllStudents(pageable);
    }

    // GET /api/students/{id} - Retrieves student profile details by student ID.
    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    // POST /api/students - Creates a new student record.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDto createStudent(@Valid @RequestBody StudentCreateRequest request) {
        return studentService.createStudent(request);
    }

    // PUT /api/students/{id} - Updates an existing student's profile by ID.
    @PutMapping("/{id}")
    public StudentDto updateStudent(
            @PathVariable Long id, 
            @Valid @RequestBody StudentUpdateRequest request) {
        return studentService.updateStudent(id, request);
    }

    // DELETE /api/students/{id} - Deletes a student record by ID.
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    // GET /api/students/{id}/gpa - Calculates and returns student's cumulative GPA and credit metrics.
    @GetMapping("/{id}/gpa")
    public GpaDto getGpa(@PathVariable Long id) {
        return studentService.getGpa(id);
    }

    // GET /api/students/{id}/transcript - Generates complete academic transcript with enrollments and GPA.
    @GetMapping("/{id}/transcript")
    public TranscriptDto getTranscript(@PathVariable Long id) {
        return studentService.getTranscript(id);
    }

    // POST /api/students/{id}/enrollments - Enrolls a student in a course.
    @PostMapping("/{id}/enrollments")
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentDto enroll(
            @PathVariable Long id, 
            @Valid @RequestBody EnrollmentRequest request) {
        return enrollmentService.enroll(id, request);
    }

    // GET /api/students/{id}/enrollments - Retrieves a student's enrollments, optionally filtered by status.
    @GetMapping("/{id}/enrollments")
    public List<EnrollmentDto> getEnrollments(
            @PathVariable Long id,
            @RequestParam(required = false) EnrollmentStatus status) {
        return enrollmentService.getStudentEnrollments(id, status);
    }
}

