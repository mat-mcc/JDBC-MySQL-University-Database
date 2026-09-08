// CourseAtCapacityException.java
// Custom runtime exception thrown when attempting to enroll a student in a course that reached max capacity.
// Mapped to HTTP 409 Conflict in GlobalExceptionHandler.
package com.mattmccaughan.university.exception;

public class CourseAtCapacityException extends RuntimeException {

    // Constructs exception with course ID and maximum allowed enrollment.
    public CourseAtCapacityException(Long courseId, int maxEnrollment) {
        super(String.format("Course %d is at capacity (%d/%d)", courseId, maxEnrollment, maxEnrollment));
    }

    // Constructs exception with a custom message.
    public CourseAtCapacityException(String message) {
        super(message);
    }
}

