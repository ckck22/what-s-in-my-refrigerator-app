package com.fridge.app.dto;

import com.fridge.app.entity.Word;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class QuizQuestionDto {
    
    // The AI-recognized English label (e.g., "apple")
    private final String labelEn;
    
    // The Korean name for the question (e.g., "사과")
    private final String nameKo;
    
    // A list of translations (e.g., "Manzana", "Pomme", "Apfel")
    private final List<TranslationDto> translations;

    // A constructor to convert a Word Entity into a QuizQuestion DTO.
    public QuizQuestionDto(Word entity) {
        this.labelEn = entity.getLabelEn();
        this.nameKo = entity.getNameKo();
        this.translations = entity.getTranslations().stream()
                .map(TranslationDto::new) // Uses the constructor from TranslationDto
                .collect(Collectors.toList());
    }
}