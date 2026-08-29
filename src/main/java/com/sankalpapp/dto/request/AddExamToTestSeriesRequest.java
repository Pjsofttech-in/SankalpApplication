package com.sankalpapp.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddExamToTestSeriesRequest {

    private Long examId;

    private Integer sequence;
}