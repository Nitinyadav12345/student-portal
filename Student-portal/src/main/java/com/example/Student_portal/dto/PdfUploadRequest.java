package com.example.Student_portal.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class PdfUploadRequest {
    private Long userId;
    private MultipartFile file;
}

