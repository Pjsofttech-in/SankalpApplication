package com.sankalpapp.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestSeriesRequest {

    private String title;

    private String description;

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
}