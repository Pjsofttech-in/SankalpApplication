package com.testapplication.service;

import com.testapplication.dto.Request.QuestionRequest;
import com.testapplication.dto.Response.QuestionResponse;

import java.util.List;

public interface QuestionService {

    QuestionResponse saveQuestion(QuestionRequest request);

    QuestionResponse updateQuestion(Long id, QuestionRequest request);

    void deleteQuestion(Long id);

    QuestionResponse getQuestionById(Long id);

    List<QuestionResponse> getAllQuestions();
}