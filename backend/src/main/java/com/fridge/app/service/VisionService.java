package com.fridge.app.service;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Automatically injects the ImageAnnotatorClient bean.
public class VisionService {

    // The 'gcp-vision' starter automatically creates this client bean for us.
    private final ImageAnnotatorClient imageAnnotatorClient;

    // This method takes an image file and returns a list of detected labels.
    public List<String> detectLabels(MultipartFile file) throws IOException {

        // Convert the image file (MultipartFile) to Google's ByteString format.
        ByteString imgBytes = ByteString.readFrom(file.getInputStream());
        Image img = Image.newBuilder().setContent(imgBytes).build();

        // Set the feature we want: LABEL_DETECTION
        Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();

        // Send the request in a batch (even if it's just one image)
        BatchAnnotateImagesResponse response =
                imageAnnotatorClient.batchAnnotateImages(List.of(request));

        // Parse the response
        AnnotateImageResponse res = response.getResponses(0);
        if (res.hasError()) {
            // If the API returned an error (e.g., bad image format)
            System.err.println("Error analyzing image: " + res.getError().getMessage());
            return new ArrayList<>(); // Return an empty list
        }

        // Filter the results:
        // We only want labels with a score > 0.7 (70% confidence)
        // and we take a maximum of 5 labels.
        return res.getLabelAnnotationsList().stream()
                .filter(label -> label.getScore() > 0.7) // Filter by confidence score
                .limit(5)                                // Get top 5 labels
                .map(EntityAnnotation::getDescription)   // Get the English description (e.g., "apple")
                .collect(Collectors.toList());
    }
}