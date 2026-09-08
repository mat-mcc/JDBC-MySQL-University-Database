// GpaDto.java
// Data Transfer Object representing calculated Grade Point Average metrics for a student.
// Summarizes cumulative GPA, total earned credits, quality points, and completed course count.
package com.mattmccaughan.university.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GpaDto {

    // Cumulative grade point average (rounded to 2 decimal places)
    private double gpa;
    
    // Total credits earned across completed courses
    private int totalCredits;
    
    // Total grade quality points accumulated (credit * point value)
    private double totalGradePoints;
    
    // Total number of completed courses contributing to the GPA
    private int completedCourses;
}

