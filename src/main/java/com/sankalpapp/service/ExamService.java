package com.sankalpapp.service;

import com.sankalpapp.dto.Request.ExamRequest;
import com.sankalpapp.dto.Request.SubmitExamRequest;
import com.sankalpapp.dto.Response.ExamResponse;
import com.sankalpapp.dto.Response.StartExamResponse;

import java.util.List;

public interface ExamService {

    ExamResponse saveExam(ExamRequest request);

    ExamResponse updateExam(Long id, ExamRequest request);

    void deleteExam(Long id);

    ExamResponse getExamById(Long id);

    List<ExamResponse> getAllExams();

}