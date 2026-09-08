// OpenApiConfig.java
// Configuration for Swagger / OpenAPI 3.0 API documentation.
// Defines metadata, API title, description, and contact details for the interactive Swagger UI.
package com.mattmccaughan.university.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    // Configures OpenAPI bean with application-level documentation metadata.
    @Bean
    public OpenAPI universityOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("University Management System API")
                        .description("REST API for managing students, departments, courses, and enrollments")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Matthew McCaughan")));
    }
}

