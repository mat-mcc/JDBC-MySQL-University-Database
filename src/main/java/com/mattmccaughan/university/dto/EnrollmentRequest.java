// EnrollmentRequest.java
// Data Transfer Object for enrolling a student into a course.
// Carries the target course ID while student context is provided via path variable.
package com.mattmccaughan.university.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class EnrollmentRequest {

    // Identifier of the course to enroll into
    @NotNull(message = "Course ID is required")
    private Long courseId;
}

