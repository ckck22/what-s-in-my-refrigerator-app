package com.fridge.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "word") // Map this class to the 'word' table in the database.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA requires a protected default constructor.
public class Word extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing ID.
    @Column(name = "word_id")
    private Long wordId;

    @Column(name = "label_en", nullable = false, unique = true) // 'label_en' column, must not be null and must be unique.
    private String labelEn;

    @Column(name = "name_ko", nullable = false) // 'name_ko' column, must not be null.
    private String nameKo;

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Translation> translations = new ArrayList<>();

    // (Optional) A helper method to add translations conveniently.
    public void addTranslation(Translation translation) {
        translations.add(translation);
        translation.setWord(this);
    }
}