// EnrollmentRepository.java
// Spring Data JPA repository for Enrollment entity operations.
// Supports lookups by student, course, and enrollment status, and provides active seat count queries.
package com.mattmccaughan.university.repository;

import com.mattmccaughan.university.entity.Enrollment;
import com.mattmccaughan.university.entity.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // Retrieves all enrollments for a given student ID.
    List<Enrollment> findByStudentId(Long studentId);

    // Retrieves enrollments for a student filtered by status (e.g., COMPLETED, ENROLLED).
    List<Enrollment> findByStudentIdAndStatus(Long studentId, EnrollmentStatus status);

    // Retrieves enrollments for a course filtered by status (e.g., building course roster).
    List<Enrollment> findByCourseIdAndStatus(Long courseId, EnrollmentStatus status);

    // Counts current enrollments for a course matching a specific status (e.g., active capacity checks).
    long countByCourseIdAndStatus(Long courseId, EnrollmentStatus status);

    // Finds a specific student's enrollment in a course with the specified status.
    Optional<Enrollment> findByStudentIdAndCourseIdAndStatus(
            Long studentId, Long courseId, EnrollmentStatus status);

    // Checks if an enrollment already exists for a student in a course with the specified status.
    boolean existsByStudentIdAndCourseIdAndStatus(
            Long studentId, Long courseId, EnrollmentStatus status);
}

