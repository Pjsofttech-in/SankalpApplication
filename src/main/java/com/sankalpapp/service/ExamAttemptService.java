package com.sankalpapp.service;

import com.sankalpapp.dto.Request.StudentAnswerRequest;
import com.sankalpapp.dto.Response.ExamResultResponse;
import com.sankalpapp.dto.Response.ExamStartResponse;
import com.sankalpapp.dto.Response.StudentQuestionResponse;

import java.util.List;

public interface ExamAttemptService {

    ExamStartResponse startExam(Long examId, Long testSeriesId);

    List<StudentQuestionResponse> getExamQuestions(
            Long attemptId
    );

    void saveAnswer(
            Long attemptId,
            StudentAnswerRequest request
    );

    ExamResultResponse submitExam(
            Long attemptId
    );

    ExamResultResponse getResult(Long attemptId);

    ExamResultResponse publishResult(Long resultId);
}