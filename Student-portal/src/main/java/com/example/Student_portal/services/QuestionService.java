package com.example.Student_portal.services;

import com.example.Student_portal.model.Question;
import com.example.Student_portal.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;
import java.util.regex.*;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> uploadPdf(MultipartFile file) throws Exception {
        Tika tika = new Tika();
        InputStream is = file.getInputStream();
        String text = tika.parseToString(is);

        List<Question> questions = new ArrayList<>();
        Pattern pattern = Pattern.compile("Q:(.*?)A:(.*?)B:(.*?)C:(.*?)D:(.*?)Ans:(\\w)", Pattern.CASE_INSENSITIVE);

        for (String line : text.split("\\r?\\n")) {
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                questions.add(Question.builder()
                        .questionText(m.group(1).trim())
                        .optionA(m.group(2).trim())
                        .optionB(m.group(3).trim())
                        .optionC(m.group(4).trim())
                        .optionD(m.group(5).trim())
                        .correctAnswer(m.group(6).trim())
                        .build());
            }
        }

        return questionRepository.saveAll(questions);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
}
