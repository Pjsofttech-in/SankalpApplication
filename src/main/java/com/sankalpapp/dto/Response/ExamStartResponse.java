package com.sankalpapp.dto.Response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamStartResponse {

    private Long attemptId;

    private Long examId;

    private String examName;

    private LocalDateTime startedAt;

    private LocalDateTime expiresAt;

    private Integer duration;

    private Integer totalQuestions;

    private Integer totalMarks;

    private String status;
}