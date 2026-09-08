// ErrorResponse.java
// Data Transfer Object representing standardized error responses across all REST API endpoints.
// Formatted consistently by GlobalExceptionHandler with timestamp, HTTP status, message, and path.
package com.mattmccaughan.university.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ErrorResponse {

    // UTC/local timestamp when the error occurred
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    
    // HTTP response status code (e.g., 400, 404, 409, 500)
    private int status;
    
    // Descriptive error message or validation summary
    private String message;
    
    // API request URI path that caused the error
    private String path;
}

