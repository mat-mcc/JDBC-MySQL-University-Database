// Student.java
// JPA Entity representing a student enrolled in the university.
// Maps to the "students" table and holds relationships to majors, minors, and course enrollments.
package com.mattmccaughan.university.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Student {

    // Primary key (auto-incremented ID)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Student first name
    @Column(name = "first_name", nullable = false)
    private String firstName;

    // Student last name
    @Column(name = "last_name", nullable = false)
    private String lastName;

    // Unique institutional email address
    @Column(nullable = false, unique = true)
    private String email;

    // Many-to-many relationship with departments declared as majors
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_majors",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    @Builder.Default
    private Set<Department> majors = new HashSet<>();

    // Many-to-many relationship with departments declared as minors
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_minors",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    @Builder.Default
    private Set<Department> minors = new HashSet<>();

    // One-to-many relationship tracking all course enrollments for this student
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Enrollment> enrollments = new HashSet<>();

    // Timestamp when student record was created
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Timestamp when student record was last updated
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

