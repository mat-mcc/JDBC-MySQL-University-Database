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

    @NotNull(message = "Status is required")
    private EnrollmentStatus status;

    private String grade;
}
