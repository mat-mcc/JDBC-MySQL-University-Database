package com.mattmccaughan.university.exception;

public class DuplicateEnrollmentException extends RuntimeException {

    public DuplicateEnrollmentException(Long studentId, Long courseId) {
        super(String.format("Student %d is already enrolled in course %d", studentId, courseId));
    }

    public DuplicateEnrollmentException(String message) {
        super(message);
    }
}
