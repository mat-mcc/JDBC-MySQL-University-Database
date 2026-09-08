// Enrollment.java
// JPA Join Entity representing a student's enrollment in a course with lifecycle status and grade.
// Replaces legacy IsTaking + HasTaken tables with a unified state machine: ENROLLED -> COMPLETED | DROPPED.
package com.mattmccaughan.university.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Join entity between Student and Course with extra columns (status, grade).
 * Replaces the legacy IsTaking + HasTaken tables.
 * <p>
 * Lifecycle: ENROLLED → COMPLETED (with grade) | DROPPED
 */
@Entity
@Table(name = "enrollments")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Enrollment {

    // Primary key (auto-incremented ID)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The enrolled student (foreign key: student_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // The enrolled course (foreign key: course_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // Status enum (ENROLLED, COMPLETED, or DROPPED)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status;

    // Final letter grade assigned upon course completion (e.g., "A", "B+")
    @Column
    private String grade;

    // Timestamp when the enrollment occurred
    @CreatedDate
    @Column(name = "enrolled_at", nullable = false, updatable = false)
    private LocalDateTime enrolledAt;

    // Timestamp when the enrollment was last modified
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

