package com.sankalpapp.service;

import com.sankalpapp.dto.Request.ResultRequest;
import com.sankalpapp.dto.Response.ExamResultResponse;

import java.util.List;

public interface ResultService {

    ExamResultResponse saveResult(ResultRequest request);

    ExamResultResponse updateResult(Long id, ResultRequest request);

    void deleteResult(Long id);

    ExamResultResponse getResultById(Long id);

    List<ExamResultResponse> getAllResults();
}