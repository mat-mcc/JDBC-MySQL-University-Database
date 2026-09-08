// StudentDto.java
// Data Transfer Object representing student details in API responses.
// Exposes student identity, contact info, and associated major/minor department names.
package com.mattmccaughan.university.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class StudentDto {

    // Unique student ID
    private Long id;
    
    // First name
    private String firstName;
    
    // Last name
    private String lastName;
    
    // Institutional email address
    private String email;
    
    // List of declared major department names
    private List<String> majors;
    
    // List of declared minor department names
    private List<String> minors;
    
    // Timestamp when student was registered
    private LocalDateTime createdAt;
}

