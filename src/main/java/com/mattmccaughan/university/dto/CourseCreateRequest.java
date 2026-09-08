// CourseCreateRequest.java
// Data Transfer Object for course creation and full update requests.
// Validates input constraints such as required course name, positive credit count, and capacity.
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

    // Unique title of the course
    @NotBlank(message = "Course name is required")
    private String name;

    // Academic credit value (must be >= 1)
    @Min(value = 1, message = "Credits must be at least 1")
    private int credits;

    // Maximum student enrollment limit (must be >= 1)
    @Min(value = 1, message = "Max enrollment must be at least 1")
    private int maxEnrollment;

    // Identifier of the department offering this course
    @NotNull(message = "Department ID is required")
    private Long departmentId;
}

