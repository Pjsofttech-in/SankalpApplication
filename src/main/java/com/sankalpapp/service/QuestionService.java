package com.sankalpapp.service;

import com.sankalpapp.dto.request.QuestionRequest;
import com.sankalpapp.dto.response.QuestionResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QuestionService {

    QuestionResponse create(QuestionRequest questionRequest,
                            MultipartFile createQuestionFile, MultipartFile optionAFile,
                            MultipartFile optionBFile,
                            MultipartFile optionCFile,
                            MultipartFile optionDFile,
                            MultipartFile answerSupportingFile);

    QuestionResponse getById(Long id);

    List<QuestionResponse> getAll();

    QuestionResponse update(Long id, QuestionRequest questionRequest,
                            MultipartFile createQuestionFile, MultipartFile optionAFile,
                            MultipartFile optionBFile,
                            MultipartFile optionCFile,
                            MultipartFile optionDFile,
                            MultipartFile answerSupportingFile);

    void delete(Long id);
}