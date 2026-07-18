package com.testapplication.serviceimpl;

import com.testapplication.dto.Response.AnswerKeyResponse;
import com.testapplication.entity.AnswerKey;
import com.testapplication.entity.Exam;
import com.testapplication.repository.AnswerKeyRepository;
import com.testapplication.repository.ExamRepository;
import com.testapplication.service.AnswerKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerKeyServiceImpl implements AnswerKeyService {

    private final AnswerKeyRepository answerKeyRepository;
    private final ExamRepository examRepository;

    @Override
    public AnswerKeyResponse saveAnswerKey(String title,
                                           String link,
                                           Long examId,
                                           Boolean active,
                                           MultipartFile pdf) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        AnswerKey answerKey = AnswerKey.builder()
                .title(title)
                .link(link)
                .active(active)
                .exam(exam)
                .build();

        try {
            answerKey.setPdfBlob(pdf.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Unable to upload PDF");
        }

        return mapToResponse(answerKeyRepository.save(answerKey));
    }

    @Override
    public AnswerKeyResponse updateAnswerKey(Long id,
                                             String title,
                                             String link,
                                             Long examId,
                                             Boolean active,
                                             MultipartFile pdf) {

        AnswerKey answerKey = answerKeyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer Key not found"));

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        answerKey.setTitle(title);
        answerKey.setLink(link);
        answerKey.setActive(active);
        answerKey.setExam(exam);

        if (pdf != null && !pdf.isEmpty()) {
            try {
                answerKey.setPdfBlob(pdf.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Unable to upload PDF");
            }
        }

        return mapToResponse(answerKeyRepository.save(answerKey));
    }

    @Override
    public void deleteAnswerKey(Long id) {

        AnswerKey answerKey = answerKeyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer Key not found"));

        answerKeyRepository.delete(answerKey);
    }

    @Override
    public AnswerKeyResponse getAnswerKeyById(Long id) {

        AnswerKey answerKey = answerKeyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer Key not found"));

        return mapToResponse(answerKey);
    }

    @Override
    public List<AnswerKeyResponse> getAllAnswerKeys() {

        return answerKeyRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public byte[] downloadPdf(Long id) {

        AnswerKey answerKey = answerKeyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer Key not found"));

        return answerKey.getPdfBlob();
    }

    private AnswerKeyResponse mapToResponse(AnswerKey answerKey) {

        return AnswerKeyResponse.builder()
                .id(answerKey.getId())
                .title(answerKey.getTitle())
                .link(answerKey.getLink())
                .active(answerKey.getActive())
                .examId(answerKey.getExam().getId())
                .examName(answerKey.getExam().getExamName())
                .build();
    }
}