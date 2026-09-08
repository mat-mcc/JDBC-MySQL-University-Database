// StudentCreateRequest.java
// Data Transfer Object for creating new student records.
// Validates first name, last name, valid email, and optional sets of major/minor department IDs.
package com.mattmccaughan.university.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class StudentCreateRequest {

    // Student's first name
    @NotBlank(message = "First name is required")
    private String firstName;

    // Student's last name
    @NotBlank(message = "Last name is required")
    private String lastName;

    // Student's institutional email address
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    // Set of department IDs chosen as majors
    private Set<Long> majorDepartmentIds;

    // Set of department IDs chosen as minors
    private Set<Long> minorDepartmentIds;
}

