package com.mattmccaughan.university.repository;

import com.mattmccaughan.university.entity.Enrollment;
import com.mattmccaughan.university.entity.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudentId(Long studentId);

    List<Enrollment> findByStudentIdAndStatus(Long studentId, EnrollmentStatus status);

    List<Enrollment> findByCourseIdAndStatus(Long courseId, EnrollmentStatus status);

    long countByCourseIdAndStatus(Long courseId, EnrollmentStatus status);

    Optional<Enrollment> findByStudentIdAndCourseIdAndStatus(
            Long studentId, Long courseId, EnrollmentStatus status);

    boolean existsByStudentIdAndCourseIdAndStatus(
            Long studentId, Long courseId, EnrollmentStatus status);
}
