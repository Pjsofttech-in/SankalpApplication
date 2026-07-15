package com.testapplication.dto.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultResponse {

    private Long id;
    private String studentName;
    private String examName;
    private Integer totalMarks;
    private Integer obtainedMarks;
    private Double percentage;
    private String grade;
    private String resultStatus;
}