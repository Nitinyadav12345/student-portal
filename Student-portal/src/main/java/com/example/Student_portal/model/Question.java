package com.example.Student_portal.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String questionText;
    @Column(nullable = true)
    private String optionA;
    @Column(nullable = true)
    private String optionB;
    @Column(nullable = true)
    private String optionC;
    @Column(nullable = true)
    private String optionD;
    @Column(nullable = true)
    private String correctAnswer;
    @Column(nullable = true)
    private String selectedAnswer;
}
