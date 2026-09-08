// DepartmentCreateRequest.java
// Data Transfer Object for creating or updating a university department.
// Enforces non-blank validation for department name and campus location.
package com.mattmccaughan.university.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class DepartmentCreateRequest {

    // Department name (e.g., "Computer Science")
    @NotBlank(message = "Department name is required")
    private String name;

    // Campus location where the department is situated (e.g., "Busch")
    @NotBlank(message = "Campus is required")
    private String campus;
}

