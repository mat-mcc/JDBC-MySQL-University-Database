package com.mattmccaughan.university.exception;

public class CourseAtCapacityException extends RuntimeException {

    public CourseAtCapacityException(Long courseId, int maxEnrollment) {
        super(String.format("Course %d is at capacity (%d/%d)", courseId, maxEnrollment, maxEnrollment));
    }

    public CourseAtCapacityException(String message) {
        super(message);
    }
}
