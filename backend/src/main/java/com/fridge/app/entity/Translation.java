package com.fridge.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Setter;

@Entity
@Table(name = "translation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Translation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "translation_id")
    private Long translationId;

    @Column(name = "language_code", nullable = false)
    private String languageCode;

    @Column(name = "translated_word", nullable = false)
    private String translatedWord;

    @Setter // Used by the Word class's addTranslation helper method.
    @ManyToOne(fetch = FetchType.LAZY) // Use Lazy loading for performance.
    @JoinColumn(name = "word_id", nullable = false) // This is the Foreign Key column ('word_id').
    private Word word;
}