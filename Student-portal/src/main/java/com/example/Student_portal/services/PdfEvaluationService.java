package com.example.Student_portal.services;

import com.example.Student_portal.model.*;
import com.example.Student_portal.repository.UserRepository;
import com.example.Student_portal.repository.UserResultRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfEvaluationService {

    private final UserResultRepository resultRepo;
    private final UserRepository userRepo;

    @Value("${huggingface.api.token}")
    private String hfApiToken;

    private final Tika tika = new Tika();

    public UserResult evaluatePdf(Long userId, MultipartFile pdfFile) throws Exception {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 1️⃣ Extract text from PDF
        String pdfText = tika.parseToString(pdfFile.getInputStream());

        // 2️⃣ Split text into questions (simple example, adapt to your PDF format)
        String[] lines = pdfText.split("\n");
        int score = 0;
        StringBuilder answersJson = new StringBuilder("{\"answers\":[");
        for (String line : lines) {
            // Assume format: Question | Options | Selected
            if (line.contains("|")) {
                String[] parts = line.split("\\|");
                String question = parts[0].trim();
                String options = parts[1].trim();
                String selected = parts[2].trim();

                String prompt = "You are a teacher. A student answered a multiple-choice question.\n" +
                        "Question: " + question + "\n" +
                        "Options: " + options + "\n" +
                        "Selected: " + selected + "\n" +
                        "Answer only 'CORRECT' or 'WRONG'.";

                String llmEval = callHuggingFaceLLM(prompt);
                boolean correct = llmEval.toUpperCase().contains("CORRECT");
                if (correct) score++;

                answersJson.append("{\"question\":\"").append(question)
                        .append("\",\"selected\":\"").append(selected)
                        .append("\",\"llmEval\":\"").append(llmEval).append("\"},");
            }
        }
        if (answersJson.charAt(answersJson.length() - 1) == ',') {
            answersJson.deleteCharAt(answersJson.length() - 1);
        }
        answersJson.append("]}");

        UserResult result = UserResult.builder()
                .user(user)
                .score(score)
                .answersJson(answersJson.toString())
                .build();

        return resultRepo.save(result);
    }

    private String callHuggingFaceLLM(String prompt) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            String jsonBody = "{\"inputs\": \"" + prompt.replace("\"", "\\\"") + "\"}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api-inference.huggingface.co/models/bigscience/bloom"))
                    .header("Authorization", "Bearer " + hfApiToken)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

        } catch (Exception e) {
            e.printStackTrace();
            return "LLM evaluation failed";
        }
    }
}
