package com.fridge.app.repository;

import com.fridge.app.entity.Translation;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository interface for Translation entity, extending JpaRepository to provide CRUD operations.
public interface TranslationRepository extends JpaRepository<Translation, Long> {
    // No additional methods needed for now.
}