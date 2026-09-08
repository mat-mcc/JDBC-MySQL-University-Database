// EnrollmentService.java
// Service layer managing course registrations, grade submissions, and enrollment lifecycle state transitions.
// Enforces duplicate enrollment prevention, course capacity constraints, and grade validation rules.
package com.mattmccaughan.university.service;

import com.mattmccaughan.university.dto.EnrollmentDto;
import com.mattmccaughan.university.dto.EnrollmentRequest;
import com.mattmccaughan.university.dto.GradeUpdateRequest;
import com.mattmccaughan.university.entity.Course;
import com.mattmccaughan.university.entity.Enrollment;
import com.mattmccaughan.university.entity.EnrollmentStatus;
import com.mattmccaughan.university.entity.Student;
import com.mattmccaughan.university.exception.CourseAtCapacityException;
import com.mattmccaughan.university.exception.DuplicateEnrollmentException;
import com.mattmccaughan.university.exception.ResourceNotFoundException;
import com.mattmccaughan.university.mapper.EnrollmentMapper;
import com.mattmccaughan.university.repository.CourseRepository;
import com.mattmccaughan.university.repository.EnrollmentRepository;
import com.mattmccaughan.university.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentMapper enrollmentMapper;

    // Enrolls a student into a course after verifying existence, capacity, and active enrollment uniqueness.
    @Transactional
    public EnrollmentDto enroll(Long studentId, EnrollmentRequest request) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", request.getCourseId()));

        // Prevent duplicate active enrollments
        if (enrollmentRepository.existsByStudentIdAndCourseIdAndStatus(studentId, course.getId(), EnrollmentStatus.ENROLLED)) {
            throw new DuplicateEnrollmentException(studentId, course.getId());
        }

        // Verify course capacity limit
        long currentEnrollment = enrollmentRepository.countByCourseIdAndStatus(course.getId(), EnrollmentStatus.ENROLLED);
        if (currentEnrollment >= course.getMaxEnrollment()) {
            throw new CourseAtCapacityException(course.getId(), course.getMaxEnrollment());
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus(EnrollmentStatus.ENROLLED);

        Enrollment saved = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDto(saved);
    }

    // Updates an enrollment's lifecycle status (e.g., assigning a grade upon completion or dropping).
    @Transactional
    public EnrollmentDto updateEnrollment(Long enrollmentId, GradeUpdateRequest request) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment", "id", enrollmentId));

        enrollment.setStatus(request.getStatus());

        // Validate and assign grade when completing a course
        if (request.getStatus() == EnrollmentStatus.COMPLETED) {
            if (request.getGrade() == null || request.getGrade().trim().isEmpty()) {
                throw new IllegalArgumentException("Grade must be provided when completing a course");
            }
            enrollment.setGrade(request.getGrade());
        } else {
            enrollment.setGrade(null); // Clear grade if dropped or moved back to enrolled
        }

        Enrollment saved = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDto(saved);
    }

    // Retrieves a specific enrollment record by ID.
    @Transactional(readOnly = true)
    public EnrollmentDto getEnrollmentById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment", "id", id));
        return enrollmentMapper.toDto(enrollment);
    }
    
    // Retrieves all enrollments for a given student, optionally filtered by status.
    @Transactional(readOnly = true)
    public List<EnrollmentDto> getStudentEnrollments(Long studentId, EnrollmentStatus status) {

        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student", "id", studentId);
        }
        
        List<Enrollment> enrollments;
        if (status != null) {
            enrollments = enrollmentRepository.findByStudentIdAndStatus(studentId, status);
        } else {
            enrollments = enrollmentRepository.findByStudentId(studentId);
        }
        return enrollmentMapper.toDtoList(enrollments);
    }
}
