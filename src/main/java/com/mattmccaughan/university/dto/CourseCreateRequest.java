package com.mattmccaughan.university.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CourseCreateRequest {

    @NotBlank(message = "Course name is required")
    private String name;

    @Min(value = 1, message = "Credits must be at least 1")
    private int credits;

    @Min(value = 1, message = "Max enrollment must be at least 1")
    private int maxEnrollment;

    @NotNull(message = "Department ID is required")
    private Long departmentId;
}
