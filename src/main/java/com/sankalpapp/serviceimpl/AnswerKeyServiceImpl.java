package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.response.AnswerKeyResponse;
import com.sankalpapp.entity.AnswerKey;
import com.sankalpapp.repository.AnswerKeyRepository;
import com.sankalpapp.service.AnswerKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerKeyServiceImpl implements AnswerKeyService {

    private final static String folder = "AnswerKey";
    private final AnswerKeyRepository answerKeyRepository;
    private final S3Service s3service;

    @Override
    public AnswerKeyResponse saveAnswerKey(String title,
                                           String link,
                                           Long examId,
                                           Boolean active,
                                           MultipartFile pdf) {

        AnswerKey answerKey = AnswerKey.builder()
                .title(title)
                .link(link)
                .active(active)
                .build();

        uploadFile(pdf, answerKey);

        return mapToResponse(answerKeyRepository.save(answerKey));
    }

    private void uploadFile(MultipartFile pdf, AnswerKey answerKey) {
        if (pdf != null) {
            try {
                String fileURL = s3service.uploadFile(pdf, folder);
                answerKey.setLink(fileURL);
            } catch (IOException e) {
                throw new RuntimeException("Unable to upload File");
            }
        }
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

        answerKey.setTitle(title);
        answerKey.setLink(link);
        answerKey.setActive(active);

        uploadFile(pdf, answerKey);

        return mapToResponse(answerKeyRepository.save(answerKey));
    }

    @Override
    public void deleteAnswerKey(Long id) {

        AnswerKey answerKey = answerKeyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer Key not found"));

        s3service.deleteFileByUrl(answerKey.getLink());

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

//    @Override
//    public byte[] downloadPdf(Long id) {
//
//        AnswerKey answerKey = answerKeyRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Answer Key not found"));
//
//        return answerKey.getPdfBlob();
//    }

    private AnswerKeyResponse mapToResponse(AnswerKey answerKey) {

        return AnswerKeyResponse.builder()
                .id(answerKey.getId())
                .title(answerKey.getTitle())
                .link(answerKey.getLink())
                .active(answerKey.getActive())
//                .examId(answerKey.getExam().getId())
//                .examName(answerKey.getExam().getExamName())
                .build();
    }
}