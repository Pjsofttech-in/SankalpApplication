package com.sankalpapp.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultQuestionResponse {

    private Long questionId;

    private String question;

    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    private String correctAnswer;
    private String studentAnswer;

    private Boolean correct;

    private Integer marks;
    private Integer marksObtained;

    private String answerExplanation;
}