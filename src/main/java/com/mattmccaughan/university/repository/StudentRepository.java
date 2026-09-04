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

    Page<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName, Pageable pageable);

    @Query("SELECT DISTINCT s FROM Student s JOIN s.majors d WHERE d.name = :deptName")
    List<Student> findByMajorDepartmentName(@Param("deptName") String departmentName);

    @Query("SELECT DISTINCT s FROM Student s JOIN s.minors d WHERE d.name = :deptName")
    List<Student> findByMinorDepartmentName(@Param("deptName") String departmentName);

    @Query("SELECT DISTINCT s FROM Student s LEFT JOIN s.majors maj LEFT JOIN s.minors min " +
           "WHERE maj.name = :deptName OR min.name = :deptName")
    List<Student> findByDepartmentName(@Param("deptName") String departmentName);

    boolean existsByEmail(String email);
}
