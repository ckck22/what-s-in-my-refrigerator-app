package com.fridge.app.dto;

import com.fridge.app.entity.Translation;
import lombok.Getter;

@Getter
public class TranslationDto {

    // The language code (e.g., "es", "fr")
    private final String languageCode;
    
    // The translated word (e.g., "Manzana")
    private final String translatedWord;

    // A constructor to convert an Entity to a DTO
    public TranslationDto(Translation entity) {
        this.languageCode = entity.getLanguageCode();
        this.translatedWord = entity.getTranslatedWord();
    }
}