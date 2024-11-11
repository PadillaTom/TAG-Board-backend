package com.padillatom.TAG_Board.model.abstracts;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.OffsetDateTime;

import static java.util.Optional.ofNullable;

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
        this.createdBy = getUsername();
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedDate = OffsetDateTime.now();
        this.modifiedBy = getUsername();
    }

    private String getUsername() {
        return ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getName).orElse("Anonymous");
    }
}
