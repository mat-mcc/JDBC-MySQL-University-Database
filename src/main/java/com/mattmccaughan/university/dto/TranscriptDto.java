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

    private StudentDto student;
    private GpaDto gpa;
    private List<EnrollmentDto> enrollments;
}
