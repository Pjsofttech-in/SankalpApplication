package com.sankalpapp.dto.mapper;

import com.sankalpapp.dto.response.ExamResultResponse;
import com.sankalpapp.dto.response.ResultQuestionResponse;
import com.sankalpapp.entity.ExamQuestion;
import com.sankalpapp.entity.Question;
import com.sankalpapp.entity.Result;
import com.sankalpapp.entity.StudentAnswer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ResultMapper {

    public ExamResultResponse toResponse(Result result) {
        return ExamResultResponse.builder()
                .resultId(result.getId())
                .attemptId(result.getAttempt().getId())
                .attemptNo(result.getAttempt().getAttemptNumber())
                .examId(result.getExam().getId())
                .examName(result.getExam().getExamName())
                .studentId(result.getStudent().getId())
                .studentName(result.getStudent().getFullName())
                .totalMarks(result.getTotalMarks())
                .obtainedMarks(result.getObtainedMarks())
                .correctQuestions(result.getCorrectQuestions())
                .incorrectQuestions(result.getIncorrectQuestions())
                .solvedQuestions(result.getSolvedQuestions())
                .unsolvedQuestions(result.getUnsolvedQuestions())
                .percentage(result.getPercentage())
                .grade(result.getGrade())
                .resultStatus(result.getResultStatus())
                .build();
    }
}