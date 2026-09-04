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

    @GetMapping
    public Page<StudentDto> getAllStudents(@ParameterObject Pageable pageable) {
        return studentService.getAllStudents(pageable);
    }

    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDto createStudent(@Valid @RequestBody StudentCreateRequest request) {
        return studentService.createStudent(request);
    }

    @PutMapping("/{id}")
    public StudentDto updateStudent(
            @PathVariable Long id, 
            @Valid @RequestBody StudentUpdateRequest request) {
        return studentService.updateStudent(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/{id}/gpa")
    public GpaDto getGpa(@PathVariable Long id) {
        return studentService.getGpa(id);
    }

    @GetMapping("/{id}/transcript")
    public TranscriptDto getTranscript(@PathVariable Long id) {
        return studentService.getTranscript(id);
    }

    @PostMapping("/{id}/enrollments")
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentDto enroll(
            @PathVariable Long id, 
            @Valid @RequestBody EnrollmentRequest request) {
        return enrollmentService.enroll(id, request);
    }

    @GetMapping("/{id}/enrollments")
    public List<EnrollmentDto> getEnrollments(
            @PathVariable Long id,
            @RequestParam(required = false) EnrollmentStatus status) {
        return enrollmentService.getStudentEnrollments(id, status);
    }
}
