// CourseDto.java
// Data Transfer Object representing course details in API responses.
// Includes course attributes, parent department name, and live enrollment count.
package com.mattmccaughan.university.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CourseDto {

    // Unique course ID
    private Long id;
    
    // Course title
    private String name;
    
    // Credit hours
    private int credits;
    
    // Maximum student capacity
    private int maxEnrollment;
    
    // Name of the offering department
    private String departmentName;
    
    // Number of currently active enrolled students
    private long currentEnrollment;
}

