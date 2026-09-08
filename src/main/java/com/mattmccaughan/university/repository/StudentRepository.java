// StudentRepository.java
// Spring Data JPA repository for Student entity operations.
// Provides case-insensitive name searching, department affiliation JPQL queries, and email uniqueness checks.
package com.mattmccaughan.university.repository;

import com.mattmccaughan.university.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Searches students whose first or last name contains the given string (case-insensitive, paginated).
    Page<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName, Pageable pageable);

    // Finds distinct students majoring in a specified department.
    @Query("SELECT DISTINCT s FROM Student s JOIN s.majors d WHERE d.name = :deptName")
    List<Student> findByMajorDepartmentName(@Param("deptName") String departmentName);

    // Finds distinct students minoring in a specified department.
    @Query("SELECT DISTINCT s FROM Student s JOIN s.minors d WHERE d.name = :deptName")
    List<Student> findByMinorDepartmentName(@Param("deptName") String departmentName);

    // Finds distinct students either majoring OR minoring in a specified department.
    @Query("SELECT DISTINCT s FROM Student s LEFT JOIN s.majors maj LEFT JOIN s.minors min " +
           "WHERE maj.name = :deptName OR min.name = :deptName")
    List<Student> findByDepartmentName(@Param("deptName") String departmentName);

    // Checks if an email is already associated with an existing student.
    boolean existsByEmail(String email);
}

