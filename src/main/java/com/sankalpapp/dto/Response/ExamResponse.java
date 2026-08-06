package com.sankalpapp.dto.Response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamResponse {

    private Long id;

    private String examName;
    private LocalDate examDate;
    private Integer totalMarks;
    private Integer totalQuestions;
    private Integer duration;

    private Long categoryId;
    private String categoryName;
}