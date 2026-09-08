// CourseRepository.java
// Spring Data JPA repository for Course entity operations.
// Provides CRUD capabilities, pagination, and department-based query lookups.
package com.mattmccaughan.university.repository;

import com.mattmccaughan.university.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Finds all courses offered by a specific department ID.
    List<Course> findByDepartmentId(Long departmentId);

    // Finds paginated courses offered by a specific department ID.
    Page<Course> findByDepartmentId(Long departmentId, Pageable pageable);

    // Checks if a course with the specified name already exists.
    boolean existsByName(String name);
}

