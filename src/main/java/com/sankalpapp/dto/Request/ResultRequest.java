package com.sankalpapp.dto.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultRequest {

    private Integer totalMarks;
    private Integer obtainedMarks;
    private Double percentage;
    private String grade;
    private String resultStatus;

    private Long studentId;
    private Long examId;
}