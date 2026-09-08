// StudentUpdateRequest.java
// Data Transfer Object for updating an existing student's profile.
// Allows modifying student personal details and replacing their major/minor department affiliations.
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
public class StudentUpdateRequest {

    // Student's updated first name
    @NotBlank(message = "First name is required")
    private String firstName;

    // Student's updated last name
    @NotBlank(message = "Last name is required")
    private String lastName;

    // Student's updated institutional email address
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    // Set of replacement department IDs for majors
    private Set<Long> majorDepartmentIds;

    // Set of replacement department IDs for minors
    private Set<Long> minorDepartmentIds;
}

