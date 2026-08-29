package com.sankalpapp.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestSeriesExamRequest {

    private Long examId;

    private Integer sequence;
}