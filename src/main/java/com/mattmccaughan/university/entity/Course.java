// Course.java
// JPA Entity representing a course offered by a university department.
// Maps to the "courses" table, defining credit hours, enrollment capacity, and parent department.
package com.mattmccaughan.university.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Course {

    // Primary key (auto-incremented ID)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique name of the course
    @Column(nullable = false, unique = true)
    private String name;

    // Number of credits awarded for completing the course
    @Column(nullable = false)
    private int credits;

    // Maximum number of active enrollments permitted
    @Column(name = "max_enrollment", nullable = false)
    private int maxEnrollment;

    // Department offering the course (foreign key: department_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    // Timestamp when course record was created
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Timestamp when course record was last updated
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

