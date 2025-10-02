package com.example.Student_portal.services;

import com.example.Student_portal.model.*;
import com.example.Student_portal.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User register(User user) {
        user.setPasswordHash(encoder.encode(user.getPasswordHash()));
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && encoder.matches(password, user.getPasswordHash())) {
            return user;
        }
        return null;
    }
}
