package com.mattmccaughan.university.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GpaDto {

    private double gpa;
    private int totalCredits;
    private double totalGradePoints;
    private int completedCourses;
}
