package com.example.Student_portal.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certificate {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // Many certificates can belong to one user
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)  // Many certificates can belong to one result
    @JoinColumn(name = "result_id", nullable = false)
    private UserResult result;

    private String certificateUrl; // URL or path to generated certificate
}
