// TranscriptDto.java
// Data Transfer Object aggregating complete academic transcript data for a student.
// Combines student personal information, computed GPA metrics, and full course enrollment history.
package com.mattmccaughan.university.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TranscriptDto {

    // Student profile information
    private StudentDto student;
    
    // Overall GPA summary and credit metrics
    private GpaDto gpa;
    
    // Complete history of course enrollments and assigned grades
    private List<EnrollmentDto> enrollments;
}

