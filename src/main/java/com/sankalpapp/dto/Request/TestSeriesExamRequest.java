package com.sankalpapp.dto.Request;

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