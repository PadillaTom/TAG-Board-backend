package com.padillatom.tagboard.model.abstracts;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditableListener.class)
public abstract class Auditable {

    private OffsetDateTime createdDate = OffsetDateTime.now();

    private OffsetDateTime modifiedDate;

    private String createdBy;

    private String modifiedBy;

    private boolean deleted;
}
