package com.coolminds.travelbridge.service;

import com.coolminds.travelbridge.config.ImageProcessorJava2D;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageService {

    @Value("${api.idanalyzer.url}")
    private String apiUrl;

    @Value("${api.idanalyzer.key}")
    private String apiKey;

    public Map<String, Object> scanImage(Map<String, String> payload) throws Exception {
        final String base64Image = payload.get("image");
        final String base64ImageBack = payload.get("imageBack");

        if (base64Image == null || base64Image.isEmpty()) {
            throw new IllegalArgumentException("Image data is missing");
        }

        // Appliquer des filtres avec Java 2D
        String processedFrontImage = ImageProcessorJava2D.processImage(base64Image);
        String processedBackImage = ImageProcessorJava2D.processImage(base64ImageBack);

        final String requestBody = "{\"document\":\"" + processedFrontImage + "\", \"documentBack\":\"" + processedBackImage + "\"}";

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("X-API-KEY", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        final HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Error from API: " + response.body());
        }

        // Parse response and filter data
        final ObjectMapper objectMapper = new ObjectMapper();
        final Map<String, Object> jsonResponse = objectMapper.readValue(response.body(), Map.class);

        final Map<String, Object> filteredResponse = new HashMap<>();
        filteredResponse.put("warning", jsonResponse.get("warning"));
        filteredResponse.put("decision", jsonResponse.get("decision"));

        return filteredResponse;
    }
}
