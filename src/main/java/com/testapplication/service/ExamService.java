package com.testapplication.service;

import com.testapplication.dto.Request.ExamRequest;
import com.testapplication.dto.Response.ExamResponse;

import java.util.List;

public interface ExamService {

    ExamResponse saveExam(ExamRequest request);

    ExamResponse updateExam(Long id, ExamRequest request);

    void deleteExam(Long id);

    ExamResponse getExamById(Long id);

    List<ExamResponse> getAllExams();
}