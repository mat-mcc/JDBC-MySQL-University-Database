// DuplicateEnrollmentException.java
// Custom runtime exception thrown when attempting to enroll a student in a course they are already taking.
// Mapped to HTTP 409 Conflict in GlobalExceptionHandler.
package com.mattmccaughan.university.exception;

public class DuplicateEnrollmentException extends RuntimeException {

    // Constructs exception with student ID and course ID.
    public DuplicateEnrollmentException(Long studentId, Long courseId) {
        super(String.format("Student %d is already enrolled in course %d", studentId, courseId));
    }

    // Constructs exception with a custom message.
    public DuplicateEnrollmentException(String message) {
        super(message);
    }
}

