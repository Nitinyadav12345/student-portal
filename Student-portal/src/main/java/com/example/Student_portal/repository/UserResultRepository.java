package com.example.Student_portal.repository;

import com.example.Student_portal.model.UserResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserResultRepository extends JpaRepository<UserResult, Long> {
    List<UserResult> findByUserId(Long userId);
}
