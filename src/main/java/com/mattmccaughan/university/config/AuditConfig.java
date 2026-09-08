// AuditConfig.java
// Configuration class enabling JPA Auditing across the application.
// Automatically populates @CreatedDate and @LastModifiedDate fields on persistent entities.
package com.mattmccaughan.university.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditConfig {
    // Activates @CreatedDate and @LastModifiedDate on all entities
    
    // JPA Auditing is a Spring Data JPA feature that automatically tracks 
    // and populates metadata fields—such as creation date, modification date, 
    // and the users who created or modified a record—when database entities are saved or updated
}

