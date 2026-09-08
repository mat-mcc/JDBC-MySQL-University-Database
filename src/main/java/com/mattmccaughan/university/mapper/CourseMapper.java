// CourseMapper.java
// Component responsible for transforming Course JPA entities into CourseDto response objects.
// Enriches domain entities with dynamic state such as current enrollment counts.
package com.mattmccaughan.university.mapper;

import com.mattmccaughan.university.dto.CourseDto;
import com.mattmccaughan.university.entity.Course;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseMapper {

    // Converts a single Course entity and active enrollment count into a CourseDto.
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

    // Converts a list of Course entities to DTOs using a custom enrollment counter function.
    public List<CourseDto> toDtoList(List<Course> courses, java.util.function.Function<Long, Long> enrollmentCounter) {
        return courses.stream()
                .map(c -> toDto(c, enrollmentCounter.apply(c.getId())))
                .toList();
    }
}

