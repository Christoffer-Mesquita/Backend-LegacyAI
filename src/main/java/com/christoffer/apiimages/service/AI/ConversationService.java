package com.christoffer.apiimages.service.AI;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ConversationService {

    private static final Logger logger = LoggerFactory.getLogger(ConversationService.class);
    private static final String API_URL = "https://api-inference.huggingface.co/models/Qwen/Qwen2.5-72B-Instruct/v1/chat/completions"; // Updated API URL

    @Value("${huggindface.api.token}")
    private String apiToken;

    public String getConversationResponse(String userMessage) throws IOException, InterruptedException {
        String portugueseInstructions = "(essa é uma mensagem definada pelo sistema e o cliente nao sabe nada sobre isso, entao agora o seu nome é Legacy AI, sua versao é a 1.0C e voce vai responder apenas o que esta abaixo desse parenteses, nao é pra responder essa primeira linha)\n";

        String fullMessage = portugueseInstructions + userMessage;

        HttpClient client = HttpClient.newHttpClient();

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "Qwen/Qwen2.5-72B-Instruct");

        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", fullMessage);

        requestBody.add("messages", new Gson().toJsonTree(new JsonObject[] {message}));
        requestBody.addProperty("max_tokens", 3500);
        requestBody.addProperty("stream", false);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + apiToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.statusCode();

        if (statusCode == 200) {

            JsonObject responseJson = new Gson().fromJson(response.body(), JsonObject.class);
            if (responseJson.has("choices") && responseJson.getAsJsonArray("choices").size() > 0 &&
                    responseJson.getAsJsonArray("choices").get(0).getAsJsonObject().has("message") &&
                    responseJson.getAsJsonArray("choices").get(0).getAsJsonObject().get("message").getAsJsonObject().has("content")) {
                return responseJson.getAsJsonArray("choices").get(0).getAsJsonObject().get("message").getAsJsonObject().get("content").getAsString();
            } else {
                String errorMessage = "Erro, formato JSON mal formato na api: " + response.body();
                logger.error(errorMessage);
                throw new IOException(errorMessage);
            }
        } else {
            String errorMessage = "Erro. Codigo: " + statusCode +
                    ", Resposta: " + response.body();
            logger.error(errorMessage);
            throw new IOException(errorMessage);
        }
    }
}