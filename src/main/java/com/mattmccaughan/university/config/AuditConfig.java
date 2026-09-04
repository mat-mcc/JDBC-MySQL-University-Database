package com.mattmccaughan.university.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditConfig {
    // Activates @CreatedDate and @LastModifiedDate on all entities
}
