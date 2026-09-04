package com.mattmccaughan.university.service;

import com.mattmccaughan.university.dto.CourseCreateRequest;
import com.mattmccaughan.university.dto.CourseDto;
import com.mattmccaughan.university.dto.EnrollmentDto;
import com.mattmccaughan.university.entity.Course;
import com.mattmccaughan.university.entity.Department;
import com.mattmccaughan.university.entity.EnrollmentStatus;
import com.mattmccaughan.university.exception.ResourceNotFoundException;
import com.mattmccaughan.university.mapper.CourseMapper;
import com.mattmccaughan.university.mapper.EnrollmentMapper;
import com.mattmccaughan.university.repository.CourseRepository;
import com.mattmccaughan.university.repository.DepartmentRepository;
import com.mattmccaughan.university.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseMapper courseMapper;
    private final EnrollmentMapper enrollmentMapper;

    @Transactional(readOnly = true)
    public Page<CourseDto> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable)
                .map(this::mapToDtoWithEnrollmentCount);
    }

    @Transactional(readOnly = true)
    public CourseDto getCourseById(Long id) {
        Course course = findCourseOrThrow(id);
        return mapToDtoWithEnrollmentCount(course);
    }

    @Transactional
    public CourseDto createCourse(CourseCreateRequest request) {
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", request.getDepartmentId()));

        Course course = new Course();
        course.setName(request.getName());
        course.setCredits(request.getCredits());
        course.setMaxEnrollment(request.getMaxEnrollment());
        course.setDepartment(department);

        Course saved = courseRepository.save(course);
        return mapToDtoWithEnrollmentCount(saved);
    }

    @Transactional
    public CourseDto updateCourse(Long id, CourseCreateRequest request) {
        Course course = findCourseOrThrow(id);
        
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", request.getDepartmentId()));

        course.setName(request.getName());
        course.setCredits(request.getCredits());
        course.setMaxEnrollment(request.getMaxEnrollment());
        course.setDepartment(department);

        Course saved = courseRepository.save(course);
        return mapToDtoWithEnrollmentCount(saved);
    }

    @Transactional
    public void deleteCourse(Long id) {
        Course course = findCourseOrThrow(id);
        courseRepository.delete(course);
    }

    @Transactional(readOnly = true)
    public List<EnrollmentDto> getRoster(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course", "id", courseId);
        }
        return enrollmentMapper.toDtoList(enrollmentRepository.findByCourseIdAndStatus(courseId, EnrollmentStatus.ENROLLED));
    }

    private Course findCourseOrThrow(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
    }

    private CourseDto mapToDtoWithEnrollmentCount(Course course) {
        long currentEnrollment = enrollmentRepository.countByCourseIdAndStatus(course.getId(), EnrollmentStatus.ENROLLED);
        return courseMapper.toDto(course, currentEnrollment);
    }
}
