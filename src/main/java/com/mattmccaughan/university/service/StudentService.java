// StudentService.java
// Service layer managing student profiles, major/minor affiliations, GPA calculation, and academic transcripts.
// Contains business logic for degree requirements, grading scale conversions, and student lifecycle management.
package com.mattmccaughan.university.service;

import com.mattmccaughan.university.dto.*;
import com.mattmccaughan.university.entity.Department;
import com.mattmccaughan.university.entity.Enrollment;
import com.mattmccaughan.university.entity.EnrollmentStatus;
import com.mattmccaughan.university.entity.Student;
import com.mattmccaughan.university.exception.ResourceNotFoundException;
import com.mattmccaughan.university.mapper.EnrollmentMapper;
import com.mattmccaughan.university.mapper.StudentMapper;
import com.mattmccaughan.university.repository.DepartmentRepository;
import com.mattmccaughan.university.repository.EnrollmentRepository;
import com.mattmccaughan.university.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentMapper studentMapper;
    private final EnrollmentMapper enrollmentMapper;

    // Retrieves a paginated list of all students.
    @Transactional(readOnly = true)
    public Page<StudentDto> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable).map(studentMapper::toDto);
    }

    // Retrieves a single student by ID.
    @Transactional(readOnly = true)
    public StudentDto getStudentById(Long id) {
        return studentMapper.toDto(findStudentOrThrow(id));
    }

    // Creates a new student and assigns their declared majors and minors.
    @Transactional
    public StudentDto createStudent(StudentCreateRequest request) {
        Student student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        
        updateMajorsAndMinors(student, request.getMajorDepartmentIds(), request.getMinorDepartmentIds());

        Student saved = studentRepository.save(student);
        return studentMapper.toDto(saved);
    }

    // Updates an existing student's profile information and academic affiliations.
    @Transactional
    public StudentDto updateStudent(Long id, StudentUpdateRequest request) {
        Student student = findStudentOrThrow(id);
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        
        updateMajorsAndMinors(student, request.getMajorDepartmentIds(), request.getMinorDepartmentIds());

        Student saved = studentRepository.save(student);
        return studentMapper.toDto(saved);
    }

    // Deletes a student from the database by ID.
    @Transactional
    public void deleteStudent(Long id) {
        Student student = findStudentOrThrow(id);
        studentRepository.delete(student);
    }

    // Computes and returns the cumulative GPA for a specific student.
    @Transactional(readOnly = true)
    public GpaDto getGpa(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student", "id", studentId);
        }
        List<Enrollment> completedEnrollments = enrollmentRepository.findByStudentIdAndStatus(studentId, EnrollmentStatus.COMPLETED);
        return calculateGpa(completedEnrollments);
    }

    // Generates a comprehensive academic transcript with student info, course enrollments, and cumulative GPA.
    @Transactional(readOnly = true)
    public TranscriptDto getTranscript(Long studentId) {
        Student student = findStudentOrThrow(studentId);
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        
        List<Enrollment> completedEnrollments = enrollments.stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.COMPLETED)
                .toList();
                
        GpaDto gpa = calculateGpa(completedEnrollments);

        return TranscriptDto.builder()
                .student(studentMapper.toDto(student))
                .enrollments(enrollmentMapper.toDtoList(enrollments))
                .gpa(gpa)
                .build();
    }

    // Helper method to look up a Student entity or throw ResourceNotFoundException.
    private Student findStudentOrThrow(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
    }

    // Helper method to synchronize major and minor department sets on a student entity.
    private void updateMajorsAndMinors(Student student, Set<Long> majorIds, Set<Long> minorIds) {
        if (majorIds != null && !majorIds.isEmpty()) {
            Set<Department> majors = new HashSet<>(departmentRepository.findAllById(majorIds));
            student.setMajors(majors);
        } else {
            student.setMajors(new HashSet<>());
        }

        if (minorIds != null && !minorIds.isEmpty()) {
            Set<Department> minors = new HashSet<>(departmentRepository.findAllById(minorIds));
            student.setMinors(minors);
        } else {
            student.setMinors(new HashSet<>());
        }
    }

    // Helper method to calculate quality points, total earned credits, and resulting GPA based on standard grading scale.
    private GpaDto calculateGpa(List<Enrollment> completedEnrollments) {
        if (completedEnrollments.isEmpty()) {
            return GpaDto.builder().gpa(0.0).totalCredits(0).totalGradePoints(0.0).build();
        }

        Map<String, Double> gradePoints = Map.ofEntries(
            Map.entry("A+", 4.0), Map.entry("A", 4.0), Map.entry("A-", 3.7),
            Map.entry("B+", 3.3), Map.entry("B", 3.0), Map.entry("B-", 2.7),
            Map.entry("C+", 2.3), Map.entry("C", 2.0), Map.entry("C-", 1.7),
            Map.entry("D+", 1.3), Map.entry("D", 1.0), Map.entry("D-", 0.7),
            Map.entry("F", 0.0)
        );

        int totalCredits = 0;
        double totalPoints = 0.0;

        for (Enrollment e : completedEnrollments) {
            String grade = e.getGrade();
            int credits = e.getCourse().getCredits();
            
            Double points = gradePoints.getOrDefault(grade != null ? grade.toUpperCase() : "", 0.0);
            
            totalCredits += credits;
            totalPoints += (points * credits);
        }

        double gpa = totalCredits > 0 ? totalPoints / totalCredits : 0.0;
        gpa = Math.round(gpa * 100.0) / 100.0; // round to 2 decimal places

        return GpaDto.builder()
                .gpa(gpa)
                .totalCredits(totalCredits)
                .totalGradePoints(totalPoints)
                .build();
    }
}
