// UniversityApplication.java
// Main entry point for the Spring Boot University Management System application.
// Bootstraps the Spring context, autoconfigures components, and launches the embedded server.
package com.mattmccaughan.university;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Enables Spring Boot autoconfiguration, component scanning, and property support.
@SpringBootApplication
public class UniversityApplication {

    // Launches the Spring Boot application.
    public static void main(String[] args) {
        SpringApplication.run(UniversityApplication.class, args);
    }
}

