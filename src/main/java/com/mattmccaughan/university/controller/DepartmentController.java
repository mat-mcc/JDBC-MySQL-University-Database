// DepartmentController.java
// REST Controller exposing endpoints for university departments.
// Maps endpoints under /api/departments for listing, creating, updating, deleting departments, and querying students by department.
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

    // GET /api/departments - Retrieves paginated list of all departments.
    @GetMapping
    public Page<DepartmentDto> getAllDepartments(@ParameterObject Pageable pageable) {
        return departmentService.getAllDepartments(pageable);
    }

    // GET /api/departments/{id} - Retrieves department details by department ID.
    @GetMapping("/{id}")
    public DepartmentDto getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    // POST /api/departments - Creates a new department.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDto createDepartment(@Valid @RequestBody DepartmentCreateRequest request) {
        return departmentService.createDepartment(request);
    }

    // PUT /api/departments/{id} - Updates an existing department by department ID.
    @PutMapping("/{id}")
    public DepartmentDto updateDepartment(
            @PathVariable Long id, 
            @Valid @RequestBody DepartmentCreateRequest request) {
        return departmentService.updateDepartment(id, request);
    }

    // DELETE /api/departments/{id} - Deletes a department by department ID.
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }

    // GET /api/departments/{name}/students - Retrieves all students majoring or minoring in the named department.
    @GetMapping("/{name}/students")
    public java.util.List<StudentDto> getStudentsInDepartment(@PathVariable String name) {
        return departmentService.getStudentsByDepartment(name);
    }
}

