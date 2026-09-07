package com.sankalpapp.dto.response;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardResponse {

    private Integer rank;

    private Long studentId;

    private String studentName;

    private Integer obtainedMarks;

    private Integer totalMarks;

    private Double percentage;

    private Long timeTakenSeconds;

    private Integer correctQuestions;

    private Integer incorrectQuestions;

    private Integer solvedQuestions;

    private Integer unsolvedQuestions;
}