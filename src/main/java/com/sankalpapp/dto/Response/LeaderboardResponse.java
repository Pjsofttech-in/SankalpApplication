package com.sankalpapp.dto.Response;

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

    private Integer totalObtainedMarks;
    private Long totalTimeTakenSeconds;
}