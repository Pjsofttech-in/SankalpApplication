package com.sankalpapp.dto.Request;

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