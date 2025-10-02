package com.example.Student_portal.controller;

import com.example.Student_portal.model.Question;
import com.example.Student_portal.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/upload")
    public ResponseEntity<List<Question>> uploadPdf(@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(questionService.uploadPdf(file));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAll() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }
}
