package com.sankalpapp.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddQuestionRequest {
    private Long questionId;
    private Integer marks;
    private Integer sequence; // Optional
}