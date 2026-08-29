package com.sankalpapp.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StartExamResponse {

    private Long attemptId;

    private Long examId;

    private String examName;

    private Integer duration;

    private Integer totalQuestions;

    private Integer totalMarks;

    private LocalDateTime startedAt;

    private LocalDateTime expiresAt;

    private List<QuestionResponse> questions;
}