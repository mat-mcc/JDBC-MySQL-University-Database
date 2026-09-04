package com.mattmccaughan.university.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CourseDto {

    private Long id;
    private String name;
    private int credits;
    private int maxEnrollment;
    private String departmentName;
    private long currentEnrollment;
}
