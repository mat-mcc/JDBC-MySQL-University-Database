package com.mattmccaughan.university.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class DepartmentCreateRequest {

    @NotBlank(message = "Department name is required")
    private String name;

    @NotBlank(message = "Campus is required")
    private String campus;
}
