package com.mattmccaughan.university.mapper;

import com.mattmccaughan.university.dto.EnrollmentDto;
import com.mattmccaughan.university.entity.Enrollment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnrollmentMapper {

    public EnrollmentDto toDto(Enrollment enrollment) {
        return EnrollmentDto.builder()
                .id(enrollment.getId())
                .studentId(enrollment.getStudent().getId())
                .studentName(enrollment.getStudent().getFirstName() + " " + enrollment.getStudent().getLastName())
                .courseId(enrollment.getCourse().getId())
                .courseName(enrollment.getCourse().getName())
                .courseCredits(enrollment.getCourse().getCredits())
                .status(enrollment.getStatus())
                .grade(enrollment.getGrade())
                .enrolledAt(enrollment.getEnrolledAt())
                .build();
    }

    public List<EnrollmentDto> toDtoList(List<Enrollment> enrollments) {
        return enrollments.stream().map(this::toDto).toList();
    }
}
