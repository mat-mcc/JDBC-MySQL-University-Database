// CourseController.java
// REST Controller exposing endpoints for university courses.
// Maps endpoints under /api/courses for listing, creating, updating, deleting courses, and viewing rosters.
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

    // GET /api/courses - Retrieves paginated list of all courses.
    @GetMapping
    public Page<CourseDto> getAllCourses(@ParameterObject Pageable pageable) {
        return courseService.getAllCourses(pageable);
    }

    // GET /api/courses/{id} - Retrieves course details by course ID.
    @GetMapping("/{id}")
    public CourseDto getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    // POST /api/courses - Creates a new course.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDto createCourse(@Valid @RequestBody CourseCreateRequest request) {
        return courseService.createCourse(request);
    }

    // PUT /api/courses/{id} - Updates an existing course by course ID.
    @PutMapping("/{id}")
    public CourseDto updateCourse(
            @PathVariable Long id, 
            @Valid @RequestBody CourseCreateRequest request) {
        return courseService.updateCourse(id, request);
    }

    // DELETE /api/courses/{id} - Deletes a course by course ID.
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }

    // GET /api/courses/{id}/roster - Retrieves list of active enrolled students for a course.
    @GetMapping("/{id}/roster")
    public List<EnrollmentDto> getRoster(@PathVariable Long id) {
        return courseService.getRoster(id);
    }
}

