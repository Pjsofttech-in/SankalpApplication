package com.sankalpapp.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestSeriesProgressExamResponse {

    private Long examId;

    private String examName;

    private Integer sequence;

    private Integer totalMarks;

    private Integer totalQuestions;

    private Integer duration;

    private String status;

    private Integer obtainedMarks;

    private Double percentage;

    private String grade;

    private Boolean published;
}