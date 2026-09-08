// EnrollmentController.java
// REST Controller exposing endpoints for enrollment and grade submissions.
// Maps endpoints under /api/enrollments for fetching enrollment details and updating grades/status.
package com.mattmccaughan.university.controller;

import com.mattmccaughan.university.dto.EnrollmentDto;
import com.mattmccaughan.university.dto.GradeUpdateRequest;
import com.mattmccaughan.university.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    // GET /api/enrollments/{id} - Retrieves enrollment details by enrollment ID.
    @GetMapping("/{id}")
    public EnrollmentDto getEnrollmentById(@PathVariable Long id) {
        return enrollmentService.getEnrollmentById(id);
    }

    // PATCH /api/enrollments/{id} - Partially updates enrollment status or assigns final letter grade.
    @PatchMapping("/{id}")
    public EnrollmentDto updateEnrollment(
            @PathVariable Long id, 
            @Valid @RequestBody GradeUpdateRequest request) {
        return enrollmentService.updateEnrollment(id, request);
    }
}

