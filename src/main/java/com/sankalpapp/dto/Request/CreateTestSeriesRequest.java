package com.sankalpapp.dto.Request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTestSeriesRequest {

    private String title;

    private String description;

    private String image;

    private Double price;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean active;
}