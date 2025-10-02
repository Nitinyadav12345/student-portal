package com.example.Student_portal.services;

import com.example.Student_portal.model.*;
import com.example.Student_portal.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CertificateService {
    private final CertificateRepository certRepo;

    public Certificate createCertificate(User user, UserResult result, String url) {
        Certificate cert = Certificate.builder()
                .user(user)
                .result(result)
                .certificateUrl(url)
                .build();
        return certRepo.save(cert);
    }

    public Optional<Certificate> getCertificate(User user, UserResult result) {
        return certRepo.findByUserAndResult(user, result);
    }
}
