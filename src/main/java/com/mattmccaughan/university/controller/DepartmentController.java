package com.mattmccaughan.university.controller;

import com.mattmccaughan.university.dto.DepartmentCreateRequest;
import com.mattmccaughan.university.dto.DepartmentDto;
import com.mattmccaughan.university.dto.StudentDto;
import com.mattmccaughan.university.service.DepartmentService;
import com.mattmccaughan.university.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springdoc.core.annotations.ParameterObject;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public Page<DepartmentDto> getAllDepartments(@ParameterObject Pageable pageable) {
        return departmentService.getAllDepartments(pageable);
    }

    @GetMapping("/{id}")
    public DepartmentDto getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDto createDepartment(@Valid @RequestBody DepartmentCreateRequest request) {
        return departmentService.createDepartment(request);
    }

    @PutMapping("/{id}")
    public DepartmentDto updateDepartment(
            @PathVariable Long id, 
            @Valid @RequestBody DepartmentCreateRequest request) {
        return departmentService.updateDepartment(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }

    @GetMapping("/{name}/students")
    public java.util.List<StudentDto> getStudentsInDepartment(@PathVariable String name) {
        return departmentService.getStudentsByDepartment(name);
    }
}
