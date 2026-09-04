package com.mattmccaughan.university.mapper;

import com.mattmccaughan.university.dto.StudentDto;
import com.mattmccaughan.university.entity.Department;
import com.mattmccaughan.university.entity.Student;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentMapper {

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

    public List<StudentDto> toDtoList(List<Student> students) {
        return students.stream().map(this::toDto).toList();
    }
}
