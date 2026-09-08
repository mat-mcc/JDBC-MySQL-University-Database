// DepartmentDto.java
// Data Transfer Object representing department details in API responses.
// Exposes department metadata alongside total count of associated courses.
package com.mattmccaughan.university.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class DepartmentDto {

    // Unique department ID
    private Long id;
    
    // Department name
    private String name;
    
    // Campus location
    private String campus;
    
    // Total number of courses offered by this department
    private int courseCount;
}

