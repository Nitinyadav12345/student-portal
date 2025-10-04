package com.example.Student_portal.services;

import com.example.Student_portal.model.*;
import com.example.Student_portal.repository.QuestionRepository;
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

@Service
@RequiredArgsConstructor
public class PdfEvaluationService {

    private final UserResultRepository resultRepo;
    private final UserRepository userRepo;
    private final QuestionRepository questionRepo;

    @Value("${groq.api.key}")
    private String groqApiKey;

    private final Tika tika = new Tika();

    public UserResult evaluatePdf(Long userId, MultipartFile pdfFile) throws Exception {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Extract text from PDF
        String pdfText = tika.parseToString(pdfFile.getInputStream());

        String[] lines = pdfText.split("\n");
        int score = 0;
        StringBuilder answersJson = new StringBuilder("{\"answers\":[");

        for (String line : lines) {
            if (line.contains("|")) {
                String[] parts = line.split("\\|");

                // Expecting: Question | A) optionA, B) optionB... | SelectedAnswer
                String questionText = parts[0].trim();
                String options = parts[1].trim();
                String selected = parts[2].trim();

                // Split options into A, B, C, D
                String[] opts = options.split(",");
                String optionA = opts.length > 0 ? opts[0].trim() : null;
                String optionB = opts.length > 1 ? opts[1].trim() : null;
                String optionC = opts.length > 2 ? opts[2].trim() : null;
                String optionD = opts.length > 3 ? opts[3].trim() : null;

                // LLM Evaluation
                String prompt = "You are a teacher. A student answered a multiple-choice question.\n" +
                                "Question: " + questionText + "\n" +
                                "Options: " + options + "\n" +
                                "Selected: " + selected + "\n" +
                                "Answer only 'CORRECT' or 'WRONG'.";

                String llmEval = callGroqLLM(prompt);
                boolean correct = llmEval.toUpperCase().contains("CORRECT");
                if (correct) score++;

                // Save Question to DB
                Question q = Question.builder()
                        .questionText(questionText)
                        .optionA(optionA)
                        .optionB(optionB)
                        .optionC(optionC)
                        .optionD(optionD)
                        .selectedAnswer(selected)
                        .correctAnswer(correct ? selected : null) // optional
                        .build();

                questionRepo.save(q);

                // For UserResult JSON
                answersJson.append("{\"question\":\"").append(questionText)
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

    private String callGroqLLM(String prompt) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            String jsonBody = String.format("""
            {
              "model": "llama3-8b-8192",
              "messages": [
                {"role": "system", "content": "You are a teacher."},
                {"role": "user", "content": "%s"}
              ],
              "temperature": 0
            }
            """, prompt.replace("\"", "\\\""));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.groq.com/openai/v1/chat/completions"))
                    .header("Authorization", "Bearer " + groqApiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String body = response.body();
            int idx = body.indexOf("\"content\":");
            if (idx != -1) {
                return body.substring(idx + 11, body.indexOf("\"", idx + 11));
            }
            return body;

        } catch (Exception e) {
            e.printStackTrace();
            return "LLM evaluation failed";
        }
    }
}
