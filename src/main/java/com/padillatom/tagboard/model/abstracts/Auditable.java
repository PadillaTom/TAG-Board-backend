package com.padillatom.tagboard.model.abstracts;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.OffsetDateTime;

@Data
@MappedSuperclass
public abstract class Auditable {

    private OffsetDateTime createdDate = OffsetDateTime.now();

    private OffsetDateTime modifiedDate;

    private String createdBy;

    private String modifiedBy;

    private boolean deleted;

    @PrePersist
    public void onPrePersist() {

        // Set createdDate if you want it always consistent with DB time or current time
        if (this.createdDate == null) {
            this.createdDate = OffsetDateTime.now();
        }

        this.createdBy = getUsername();
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedDate = OffsetDateTime.now();
        this.modifiedBy = getUsername();
    }

    private String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return "Anonymous or System";
        }
        return auth.getName();
    }
}
