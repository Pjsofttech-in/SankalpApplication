package com.sankalpapp.dto.mapper;

import com.sankalpapp.dto.Response.ExamResultResponse;
import com.sankalpapp.entity.Result;
import org.springframework.stereotype.Component;

@Component
public class ResultMapper {

    public ExamResultResponse toResponse(
            Result result
    ) {

        return ExamResultResponse.builder()
                .resultId(result.getId())
                .attemptId(
                        result.getAttempt()
                                .getId()
                )
                .examId(
                        result.getExam()
                                .getId()
                )
                .examName(
                        result.getExam()
                                .getExamName()
                )
                .totalMarks(
                        result.getTotalMarks()
                )
                .obtainedMarks(
                        result.getObtainedMarks()
                )
                .percentage(
                        result.getPercentage()
                )
                .grade(
                        result.getGrade()
                )
                .resultStatus(
                        result.getResultStatus()
                )
                .published(
                        result.getPublished()
                )
                .build();
    }
}