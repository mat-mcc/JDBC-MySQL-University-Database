// Department.java
// JPA Entity representing an academic department within the university.
// Maps to the "departments" table and maintains associations to offered courses.
package com.mattmccaughan.university.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Department {

    // Primary key (auto-incremented ID)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique department name (e.g., "Computer Science")
    @Column(nullable = false, unique = true)
    private String name;

    // Campus location where the department is situated
    @Column(nullable = false)
    private String campus;

    // List of courses offered by this department
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Course> courses = new ArrayList<>();

    // Timestamp when department record was created
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Timestamp when department record was last updated
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

