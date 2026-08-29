package com.sankalpapp.service;

import com.sankalpapp.dto.request.StudentAnswerRequest;
import com.sankalpapp.dto.response.ExamResultResponse;
import com.sankalpapp.dto.response.ExamStartResponse;
import com.sankalpapp.dto.response.StudentQuestionResponse;
import jakarta.transaction.Transactional;

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

    @Transactional
    List<ExamResultResponse> publishAllResults(List<Long> resultIds);
}