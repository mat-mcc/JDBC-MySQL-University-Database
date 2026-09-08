// GradeUpdateRequest.java
// Data Transfer Object for patching enrollment status and assigning grades.
// Used when concluding a course with a grade or updating an enrollment to DROPPED/ENROLLED.
package com.mattmccaughan.university.dto;

import com.mattmccaughan.university.entity.EnrollmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GradeUpdateRequest {

    // Target status to transition the enrollment to
    @NotNull(message = "Status is required")
    private EnrollmentStatus status;

    // Assigned letter grade (required when status is COMPLETED)
    private String grade;
}

