package com.example.Student_portal.controller;

import com.example.Student_portal.model.*;
import com.example.Student_portal.repository.UserRepository;
import com.example.Student_portal.services.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;
    private final UserRepository userRepo;

    @PostMapping("/submit")
    public ResponseEntity<UserResult> submitResult(
            @RequestParam Long userId,
            @RequestBody Map<Long, String> answers) {

        User user = userRepo.findById(userId).orElseThrow();
        return ResponseEntity.ok(resultService.evaluateAnswers(user, answers));
    }
}
