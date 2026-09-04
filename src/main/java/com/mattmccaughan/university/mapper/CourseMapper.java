package com.mattmccaughan.university.mapper;

import com.mattmccaughan.university.dto.CourseDto;
import com.mattmccaughan.university.entity.Course;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseMapper {

    public CourseDto toDto(Course course, long currentEnrollment) {
        return CourseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .credits(course.getCredits())
                .maxEnrollment(course.getMaxEnrollment())
                .departmentName(course.getDepartment().getName())
                .currentEnrollment(currentEnrollment)
                .build();
    }

    public List<CourseDto> toDtoList(List<Course> courses, java.util.function.Function<Long, Long> enrollmentCounter) {
        return courses.stream()
                .map(c -> toDto(c, enrollmentCounter.apply(c.getId())))
                .toList();
    }
}
