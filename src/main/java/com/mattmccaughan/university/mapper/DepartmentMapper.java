// DepartmentMapper.java
// Component responsible for transforming Department JPA entities into DepartmentDto response objects.
// Computes related metadata like associated course count.
package com.mattmccaughan.university.mapper;

import com.mattmccaughan.university.dto.DepartmentDto;
import com.mattmccaughan.university.entity.Department;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentMapper {

    // Converts a single Department entity into a DepartmentDto.
    public DepartmentDto toDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .campus(department.getCampus())
                .courseCount(department.getCourses() != null ? department.getCourses().size() : 0)
                .build();
    }

    // Converts a list of Department entities into a list of DepartmentDto objects.
    public List<DepartmentDto> toDtoList(List<Department> departments) {
        return departments.stream().map(this::toDto).toList();
    }
}

