package com.sankalpapp.dto.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentQuestionResponse {

    private Long questionId;

    private String question;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private Integer marks;

    private Integer sequence;
}