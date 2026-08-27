package com.sankalpapp.service;

import com.sankalpapp.dto.Request.ExamRequest;
import com.sankalpapp.dto.Response.ExamResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExamService {

    ExamResponse saveExam(ExamRequest request, MultipartFile image);

    ExamResponse updateExam(Long id, ExamRequest request, MultipartFile image);

    void deleteExam(Long id);

    ExamResponse getExamById(Long id);

    List<ExamResponse> getAllExams();

}