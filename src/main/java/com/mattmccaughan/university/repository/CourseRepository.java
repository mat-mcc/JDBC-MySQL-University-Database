package com.mattmccaughan.university.repository;

import com.mattmccaughan.university.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByDepartmentId(Long departmentId);

    Page<Course> findByDepartmentId(Long departmentId, Pageable pageable);

    boolean existsByName(String name);
}
