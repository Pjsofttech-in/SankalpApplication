package com.sankalpapp.service;

import com.sankalpapp.dto.request.ResultRequest;
import com.sankalpapp.dto.response.ExamResultResponse;

import java.util.List;

public interface ResultService {
    List<ExamResultResponse> getAllResults();

    List<ExamResultResponse> getAllResultsByStudentId(Long studentId);
}