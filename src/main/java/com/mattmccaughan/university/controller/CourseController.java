package com.mattmccaughan.university.controller;

import com.mattmccaughan.university.dto.CourseCreateRequest;
import com.mattmccaughan.university.dto.CourseDto;
import com.mattmccaughan.university.dto.EnrollmentDto;
import com.mattmccaughan.university.entity.EnrollmentStatus;
import com.mattmccaughan.university.service.CourseService;
import com.mattmccaughan.university.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public Page<CourseDto> getAllCourses(@ParameterObject Pageable pageable) {
        return courseService.getAllCourses(pageable);
    }

    @GetMapping("/{id}")
    public CourseDto getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDto createCourse(@Valid @RequestBody CourseCreateRequest request) {
        return courseService.createCourse(request);
    }

    @PutMapping("/{id}")
    public CourseDto updateCourse(
            @PathVariable Long id, 
            @Valid @RequestBody CourseCreateRequest request) {
        return courseService.updateCourse(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }

    @GetMapping("/{id}/roster")
    public List<EnrollmentDto> getRoster(@PathVariable Long id) {
        return courseService.getRoster(id);
    }
}
