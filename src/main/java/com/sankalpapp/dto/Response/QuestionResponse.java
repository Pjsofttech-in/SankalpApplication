package com.sankalpapp.dto.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponse {

    private Long id;

    private String question;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private String correctAnswer;

    private Integer marks;

    private Integer sequence;

    private Long examId;

    private Long sectionId;
    private String sectionName;

    private String examName;
    private String questionType;
    private String answerExplanation;
    private Boolean active;
}