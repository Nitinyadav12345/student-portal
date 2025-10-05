package com.example.Student_portal.services;

import com.example.Student_portal.dto.UserResultDTO;
import com.example.Student_portal.model.*;
import com.example.Student_portal.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final QuestionRepository questionRepo;
    private final UserResultRepository userResultRepository;

    public List<UserResultDTO> getResultsByUserId(Long userId) {
        List<UserResult> results = userResultRepository.findByUserId(userId);

        return results.stream()
                .map(result -> UserResultDTO.builder()
                        .id(result.getId())
                        .score(result.getScore())
                        .answersJson(result.getAnswersJson())
                        .user(result.getUser()) // full user object
                        .build())
                .collect(Collectors.toList());
    }
}
