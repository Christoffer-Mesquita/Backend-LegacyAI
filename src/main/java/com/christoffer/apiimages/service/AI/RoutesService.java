package com.christoffer.apiimages.service.AI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class RoutesService {
    private static final Logger logger = LoggerFactory.getLogger(RoutesService.class);
    private static final String API_URL = "https://api-inference.huggingface.co/models/ZB-Tech/Text-to-Image";
    private static final String API_TOKEN = "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";

    public String giveVersion() {
        return "1.0.0";
    }

    public byte[] generateimageFromPrompt(String prompt) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_TOKEN)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"inputs\": \"" + prompt + "\"}"))
                .build();

        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        int statusCode = response.statusCode();

        if (statusCode == 200) {
            return response.body();
        } else {
            String errorMessage = "Erro: " + statusCode +
                    ", Response body: " + new String(response.body());
            logger.error(errorMessage);
            throw new IOException(errorMessage);
        }
    }

}

