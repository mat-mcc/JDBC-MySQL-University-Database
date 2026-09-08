// GlobalExceptionHandler.java
// Centralized exception handler intercepting errors across all REST controllers.
// Translates business and validation exceptions into standardized ErrorResponse payloads with proper HTTP status codes.
package com.mattmccaughan.university.exception;

import com.mattmccaughan.university.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles missing entities by returning HTTP 404 Not Found.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    // Handles duplicate active enrollment attempts by returning HTTP 409 Conflict.
    @ExceptionHandler(DuplicateEnrollmentException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEnrollment(DuplicateEnrollmentException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    // Handles course capacity limits being exceeded by returning HTTP 409 Conflict.
    @ExceptionHandler(CourseAtCapacityException.class)
    public ResponseEntity<ErrorResponse> handleCapacity(CourseAtCapacityException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    // Handles bean validation failures (@Valid) by returning HTTP 400 Bad Request with all violated constraints.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .sorted()
                .collect(Collectors.joining("; "));
        return buildResponse(HttpStatus.BAD_REQUEST, message, request);
    }

    // Handles illegal arguments (e.g., missing grade on course completion) by returning HTTP 400 Bad Request.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    // Fallback handler for uncaught unexpected exceptions returning HTTP 500 Internal Server Error.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception at " + request.getRequestURI(), ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", request);
    }

    // Helper method to construct consistent ErrorResponse body wrapped in a ResponseEntity.
    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message, HttpServletRequest request) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .message(message)
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(error, status);
    }
}

