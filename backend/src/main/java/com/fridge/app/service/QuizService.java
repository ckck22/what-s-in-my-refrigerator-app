package com.fridge.app.service;

import com.fridge.app.dto.QuizQuestionDto;
import com.fridge.app.entity.Word;
import com.fridge.app.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Automatically injects WordRepository and VisionService.
public class QuizService {

    private final WordRepository wordRepository;
    private final VisionService visionService;

    // @Transactional(readOnly = true) ensures this method is fast and safe for reading data.
    @Transactional(readOnly = true)
    public List<QuizQuestionDto> generateQuizFromImage(MultipartFile file) throws IOException {

        // 1. Call VisionService to get English labels from the image.
        // (e.g., ["apple", "grape", "chair"])
        List<String> labels = visionService.detectLabels(file);

        // 2. For each label, find the corresponding Word entity in our DB.
        return labels.stream()
                .map(label -> {
                    // Use the method we defined in WordRepository.
                    // This returns an Optional<Word>.
                    return wordRepository.findByLabelEn(label);
                })
                .filter(Optional::isPresent) // Filter out labels that are not in our DB (e.g., "chair")
                .map(Optional::get)          // Get the Word entity from the Optional
                .map(QuizQuestionDto::new)   // 3. Convert the Word entity into a QuizQuestionDto
                .collect(Collectors.toList()); // 4. Return the final list of quiz questions.
    }
}