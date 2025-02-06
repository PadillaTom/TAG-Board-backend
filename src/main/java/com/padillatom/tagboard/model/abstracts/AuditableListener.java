package com.padillatom.tagboard.model.abstracts;

import com.padillatom.tagboard.config.SecurityService;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class AuditableListener {

    private static SecurityService securityService;

    @Autowired
    public static void setSecurityService(SecurityService securityService) {
        AuditableListener.securityService = securityService;
    }

    @PrePersist
    public void onPrePersist(Auditable entity) {
        entity.setCreatedDate(OffsetDateTime.now());
        entity.setCreatedBy(securityService != null ? securityService.getAuthenticatedUsername() : "System");
    }

    @PreUpdate
    public void onPreUpdate(Auditable entity) {
        entity.setModifiedDate(OffsetDateTime.now());
        entity.setModifiedBy(securityService != null ? securityService.getAuthenticatedUsername() : "System");
    }
}