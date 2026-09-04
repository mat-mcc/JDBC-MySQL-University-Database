package com.mattmccaughan.university.entity;

/**
 * Enrollment lifecycle status.
 * Replaces the legacy IsTaking/HasTaken split with a single
 * state machine: ENROLLED → COMPLETED | DROPPED.
 */
public enum EnrollmentStatus {
    ENROLLED,
    COMPLETED,
    DROPPED
}
