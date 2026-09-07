package com.sankalpapp.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamResultResponse {

    private Long resultId;

    private Long attemptId;

    private Integer rank;

    private Long examId;
    private Long studentId;

    private String examName;
    private String studentName;

    private Integer totalMarks;

    private Integer obtainedMarks;

    private Double percentage;

    private String grade;

    private String resultStatus;

    private Boolean published;

    private Integer correctQuestions;

    private Integer incorrectQuestions;

    private Integer solvedQuestions;

    private Integer unsolvedQuestions;

    private List<ResultQuestionResponse> questions;
}