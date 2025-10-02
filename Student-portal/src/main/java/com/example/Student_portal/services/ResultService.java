package com.example.Student_portal.services;

import com.example.Student_portal.model.*;
import com.example.Student_portal.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final UserResultRepository resultRepo;
    private final QuestionRepository questionRepo;
}
