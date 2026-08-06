package com.sankalpapp.dto.Request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamRequest {

    private String examName;
    private LocalDate examDate;
    private Integer totalMarks;
    private Integer totalQuestions;
    private Integer duration;

    private Long categoryId;
}