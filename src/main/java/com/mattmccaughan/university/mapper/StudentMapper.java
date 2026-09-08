// StudentMapper.java
// Component responsible for transforming Student JPA entities into StudentDto response objects.
// Extracts and alphabetizes major and minor department name lists.
package com.mattmccaughan.university.mapper;

import com.mattmccaughan.university.dto.StudentDto;
import com.mattmccaughan.university.entity.Department;
import com.mattmccaughan.university.entity.Student;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentMapper {

    // Converts a Student entity into a StudentDto with sorted major and minor department names.
    public StudentDto toDto(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .majors(student.getMajors().stream()
                        .map(Department::getName)
                        .sorted()
                        .toList())
                .minors(student.getMinors().stream()
                        .map(Department::getName)
                        .sorted()
                        .toList())
                .createdAt(student.getCreatedAt())
                .build();
    }

    // Converts a list of Student entities into a list of StudentDto objects.
    public List<StudentDto> toDtoList(List<Student> students) {
        return students.stream().map(this::toDto).toList();
    }
}

