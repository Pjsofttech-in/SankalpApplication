package com.sankalpapp.service;

import com.sankalpapp.dto.Response.LeaderboardResponse;

import java.util.List;

public interface LeaderboardPdfService {

    byte[] generateExamLeaderboardPdf(
            Long examId,
            List<LeaderboardResponse> leaderboard
    );

    byte[] generateTestSeriesLeaderboardPdf(
            Long testSeriesId,
            List<LeaderboardResponse> leaderboard
    );
}