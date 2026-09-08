// EnrollmentDto.java
// Data Transfer Object representing detailed enrollment information in API responses.
// Flattens student and course entities into a consolidated view with status and grade.
package com.mattmccaughan.university.dto;

import com.mattmccaughan.university.entity.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class EnrollmentDto {

    // Unique enrollment record ID
    private Long id;
    
    // Identifier of the enrolled student
    private Long studentId;
    
    // Full name of the student
    private String studentName;
    
    // Identifier of the course
    private Long courseId;
    
    // Title of the course
    private String courseName;
    
    // Credit hours of the course
    private int courseCredits;
    
    // Current enrollment status (ENROLLED, COMPLETED, or DROPPED)
    private EnrollmentStatus status;
    
    // Final letter grade earned (null if active or dropped)
    private String grade;
    
    // Timestamp when the student enrolled
    private LocalDateTime enrolledAt;
}

