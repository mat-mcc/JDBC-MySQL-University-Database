// ResourceNotFoundException.java
// Custom runtime exception thrown when an entity cannot be found in the database by its lookup criteria.
// Mapped to HTTP 404 Not Found in GlobalExceptionHandler.
package com.mattmccaughan.university.exception;

public class ResourceNotFoundException extends RuntimeException {

    // Constructs exception with resource name, field identifier, and looked-up value.
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: %s", resourceName, fieldName, fieldValue));
    }

    // Constructs exception with a custom message.
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

