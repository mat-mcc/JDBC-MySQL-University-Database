// DepartmentRepository.java
// Spring Data JPA repository for Department entity operations.
// Provides CRUD capabilities and case-insensitive department lookups.
package com.mattmccaughan.university.repository;

import com.mattmccaughan.university.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Finds a department by name ignoring case.
    Optional<Department> findByNameIgnoreCase(String name);

    // Checks if a department exists with the given name ignoring case.
    boolean existsByNameIgnoreCase(String name);
}

