package com.example.Student_portal.dto;

import com.example.Student_portal.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResultDTO {
    private Long id;
    private int score;
    private String answersJson;
    private User user; // complete User object
}
