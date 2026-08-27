package com.sankalpapp.dto.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamResultResponse {

    private Long resultId;

    private Long attemptId;

    private Long examId;

    private String examName;

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
}