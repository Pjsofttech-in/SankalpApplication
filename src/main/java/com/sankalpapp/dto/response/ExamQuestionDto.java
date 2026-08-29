package com.sankalpapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamQuestionDto {

    private Long id;
    private Long examId;
    private Long questionId;
    private Integer sequence;
    private Integer marks;
    private Boolean active;
}