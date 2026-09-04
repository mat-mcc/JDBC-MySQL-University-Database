package com.mattmccaughan.university.mapper;

import com.mattmccaughan.university.dto.DepartmentDto;
import com.mattmccaughan.university.entity.Department;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentMapper {

    public DepartmentDto toDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .campus(department.getCampus())
                .courseCount(department.getCourses() != null ? department.getCourses().size() : 0)
                .build();
    }

    public List<DepartmentDto> toDtoList(List<Department> departments) {
        return departments.stream().map(this::toDto).toList();
    }
}
