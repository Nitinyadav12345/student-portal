package com.example.Student_portal.controller;

import com.example.Student_portal.dto.PdfUploadRequest;
import com.example.Student_portal.model.UserResult;
import com.example.Student_portal.services.PdfEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public class PdfController {

    private final PdfEvaluationService pdfService;

    @PostMapping("/upload")
    public UserResult uploadPdf(@RequestParam("userId") Long userId,
                                @RequestParam("file") MultipartFile file) throws Exception {
        return pdfService.evaluatePdf(userId, file);
    }
}
