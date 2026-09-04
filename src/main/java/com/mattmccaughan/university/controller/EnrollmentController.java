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

    @GetMapping("/{id}")
    public EnrollmentDto getEnrollmentById(@PathVariable Long id) {
        return enrollmentService.getEnrollmentById(id);
    }

    @PatchMapping("/{id}")
    public EnrollmentDto updateEnrollment(
            @PathVariable Long id, 
            @Valid @RequestBody GradeUpdateRequest request) {
        return enrollmentService.updateEnrollment(id, request);
    }
}
