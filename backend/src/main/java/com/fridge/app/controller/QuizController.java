package com.fridge.app.controller;

import com.fridge.app.dto.QuizQuestionDto;
import com.fridge.app.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

// @RestController: This tells Spring that this class is a Controller
// and that its methods will return JSON responses (not HTML).
@RestController
// @RequestMapping("/api"): Sets a base URL for all methods in this controller.
// So, our endpoint will be "/api" + "/quiz/generate".
@RequestMapping("/api")
// @RequiredArgsConstructor: Automatically injects the QuizService.
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    // @PostMapping: This method handles HTTP POST requests.
    // It listens at the URL "/quiz/generate" (combined with "/api" from above).
    @PostMapping("/quiz/generate")
    public ResponseEntity<?> generateQuiz(
            // @RequestParam("image"): Expects a file in the request part named "image".
            // This is what React Native will need to name its file upload.
            @RequestParam("image") MultipartFile imageFile) {

        // Basic check to see if the file is empty.
        if (imageFile.isEmpty()) {
            return ResponseEntity.badRequest().body("Image file is empty.");
        }

        try {
            // 1. Pass the file to the service to get the list of quiz questions.
            List<QuizQuestionDto> quiz = quizService.generateQuizFromImage(imageFile);

            // 2. Return the list of DTOs as a JSON array with a 200 OK status.
            return ResponseEntity.ok(quiz);

        } catch (IOException e) {
            // Handle error if image processing fails (e..g, bad file)
            e.printStackTrace(); // Log the error to the console
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing image: " + e.getMessage());
        } catch (Exception e) {
            // Handle any other unexpected errors.
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred.");
        }
    }
}