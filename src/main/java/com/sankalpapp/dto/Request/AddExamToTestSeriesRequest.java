package com.sankalpapp.dto.Request;

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