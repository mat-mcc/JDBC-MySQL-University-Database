// EnrollmentStatus.java
// Enumeration defining the lifecycle states of a course enrollment.
// Replaces the legacy IsTaking / HasTaken table split with a unified state model.
package com.mattmccaughan.university.entity;

public enum EnrollmentStatus {
    // Student is currently actively attending the course
    ENROLLED,
    
    // Student has completed the course and earned a final grade
    COMPLETED,
    
    // Student dropped the course prior to completion
    DROPPED
}
