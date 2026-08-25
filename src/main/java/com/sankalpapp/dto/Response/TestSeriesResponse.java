package com.sankalpapp.dto.Response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestSeriesResponse {

    private Long id;

    private String title;

    private String description;

    private Boolean active;

    private List<TestSeriesExamResponse> exams;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}