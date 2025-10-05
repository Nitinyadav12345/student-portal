package com.example.Student_portal.controller;

import com.example.Student_portal.dto.UserResultDTO;
import com.example.Student_portal.services.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;
    @GetMapping("/user/{userId}")
    public List<UserResultDTO> getResultsByUser(@PathVariable Long userId) {
        return resultService.getResultsByUserId(userId);
    }
}
