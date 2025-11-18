package com.fridge.app.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // Indicates that this class is a base class for JPA entities.
@EntityListeners(AuditingEntityListener.class) // Enable Auditing for this class.
public abstract class BaseTimeEntity {

    @CreatedDate // Automatically set the creation time when an entity is first saved.
    private LocalDateTime createdAt;

    @LastModifiedDate // Automatically update the modification time when an entity is updated.
    private LocalDateTime updatedAt;
}