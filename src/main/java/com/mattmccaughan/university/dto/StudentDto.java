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

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> majors;
    private List<String> minors;
    private LocalDateTime createdAt;
}
