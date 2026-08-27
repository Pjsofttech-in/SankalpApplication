package com.sankalpapp.service;

import com.sankalpapp.dto.Response.LeaderboardResponse;

import java.util.List;

public interface LeaderboardService {

    List<LeaderboardResponse> getExamLeaderboard(
            Long examId
    );

    List<LeaderboardResponse> getTestSeriesLeaderboard(
            Long testSeriesId
    );

    void finalizeExamLeaderboard(
            Long examId
    );

    void finalizeTestSeriesLeaderboard(
            Long testSeriesId
    );

    byte[] generateExamLeaderboardPdf(
            Long examId
    );
}