package com.sankalpapp.dto.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestSeriesExamResponse {

    private Long id;

    private Long examId;

    private String examName;

    private Integer sequence;

    private Integer totalQuestions;

    private Integer totalMarks;

    private Integer duration;

    private Boolean active;
}