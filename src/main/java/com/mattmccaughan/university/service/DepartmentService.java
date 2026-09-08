// DepartmentService.java
// Service layer managing business logic for university departments.
// Provides CRUD operations for departments and queries for students affiliated with a department.
package com.mattmccaughan.university.service;

import com.mattmccaughan.university.dto.DepartmentCreateRequest;
import com.mattmccaughan.university.dto.DepartmentDto;
import com.mattmccaughan.university.dto.StudentDto;
import com.mattmccaughan.university.entity.Department;
import com.mattmccaughan.university.exception.ResourceNotFoundException;
import com.mattmccaughan.university.mapper.DepartmentMapper;
import com.mattmccaughan.university.mapper.StudentMapper;
import com.mattmccaughan.university.repository.DepartmentRepository;
import com.mattmccaughan.university.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;
    private final DepartmentMapper departmentMapper;
    private final StudentMapper studentMapper;

    // Retrieves a paginated list of all departments.
    @Transactional(readOnly = true)
    public Page<DepartmentDto> getAllDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable).map(departmentMapper::toDto);
    }

    // Retrieves a single department by its ID.
    @Transactional(readOnly = true)
    public DepartmentDto getDepartmentById(Long id) {
        return departmentMapper.toDto(findDepartmentOrThrow(id));
    }

    // Creates and persists a new academic department.
    @Transactional
    public DepartmentDto createDepartment(DepartmentCreateRequest request) {
        Department department = new Department();
        department.setName(request.getName());
        department.setCampus(request.getCampus());
        
        Department saved = departmentRepository.save(department);
        return departmentMapper.toDto(saved);
    }

    // Updates name and campus location for an existing department.
    @Transactional
    public DepartmentDto updateDepartment(Long id, DepartmentCreateRequest request) {
        Department department = findDepartmentOrThrow(id);
        department.setName(request.getName());
        department.setCampus(request.getCampus());
        
        Department saved = departmentRepository.save(department);
        return departmentMapper.toDto(saved);
    }

    // Deletes a department from the database by ID.
    @Transactional
    public void deleteDepartment(Long id) {
        Department department = findDepartmentOrThrow(id);
        departmentRepository.delete(department);
    }

    // Retrieves all students either majoring or minoring in the given department.
    @Transactional(readOnly = true)
    public List<StudentDto> getStudentsByDepartment(String departmentName) {
        return studentMapper.toDtoList(studentRepository.findByDepartmentName(departmentName));
    }

    // Helper method to look up a Department entity or throw ResourceNotFoundException.
    private Department findDepartmentOrThrow(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", id));
    }
}
