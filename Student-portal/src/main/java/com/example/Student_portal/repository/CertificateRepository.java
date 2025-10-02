package com.example.Student_portal.repository;


import com.example.Student_portal.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Optional<Certificate> findByUserAndResult(User user, UserResult result);
}

