package com.sankalpapp.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestSeriesProgressResponse {

    private Long testSeriesId;

    private String title;

    private String description;

    private Integer totalExams;

    private Integer completedExams;

    private Double overallPercentage;

    private List<TestSeriesProgressExamResponse> exams;
}