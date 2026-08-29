package com.sankalpapp.dto.response;

import lombok.*;

import java.time.LocalDate;
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
    private String image;

    private Boolean active;

    private Double price;

    private Double sellingPrice;
    private Double mrp;
    private String testFeatureOne;
    private String testFeatureTwo;
    private String testFeatureThree;
    private String subject;
    private String seo;

    private Long categoryId;
    private LocalDate startDate;
    private LocalDate endDate;

    private List<TestSeriesExamResponse> exams;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}