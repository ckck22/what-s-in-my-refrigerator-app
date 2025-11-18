package com.fridge.app.repository;

import com.fridge.app.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Repository interface for Word entity, extending JpaRepository to provide CRUD operations.
public interface WordRepository extends JpaRepository<Word, Long> {

    // Custom query method to find a Word by its English label.
    Optional<Word> findByLabelEn(String labelEn);
}