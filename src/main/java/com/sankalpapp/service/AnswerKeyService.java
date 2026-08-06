package com.sankalpapp.service;

import com.sankalpapp.dto.Response.AnswerKeyResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AnswerKeyService {

    AnswerKeyResponse saveAnswerKey(String title,
                                    String link,
                                    Long examId,
                                    Boolean active,
                                    MultipartFile pdf);

    AnswerKeyResponse updateAnswerKey(Long id,
                                      String title,
                                      String link,
                                      Long examId,
                                      Boolean active,
                                      MultipartFile pdf);

    void deleteAnswerKey(Long id);

    AnswerKeyResponse getAnswerKeyById(Long id);

    List<AnswerKeyResponse> getAllAnswerKeys();

    byte[] downloadPdf(Long id);
}