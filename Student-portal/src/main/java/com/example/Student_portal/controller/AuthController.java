package com.example.Student_portal.controller;

import com.example.Student_portal.model.User;
import com.example.Student_portal.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User u = authService.login(user.getUsername(), user.getPasswordHash());
        if (u != null) return ResponseEntity.ok(u);
        return ResponseEntity.status(401).build();
    }
}
