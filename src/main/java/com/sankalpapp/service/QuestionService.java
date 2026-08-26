package com.sankalpapp.service;

import com.sankalpapp.dto.Request.QuestionRequest;
import com.sankalpapp.dto.Response.QuestionResponse;

import java.util.List;

public interface QuestionService {

    QuestionResponse create(QuestionRequest questionRequest);

    QuestionResponse getById(Long id);

    List<QuestionResponse> getAll();

    QuestionResponse update(Long id, QuestionRequest questionRequest);

    void delete(Long id);
}