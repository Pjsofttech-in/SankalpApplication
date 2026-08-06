package com.sankalpapp.service;

import com.sankalpapp.dto.Request.QuestionRequest;
import com.sankalpapp.dto.Response.QuestionResponse;

import java.util.List;

public interface QuestionService {

    QuestionResponse saveQuestion(QuestionRequest request);

    QuestionResponse updateQuestion(Long id, QuestionRequest request);

    void deleteQuestion(Long id);

    QuestionResponse getQuestionById(Long id);

    List<QuestionResponse> getAllQuestions();
}