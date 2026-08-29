package com.sankalpapp.controller;

import com.sankalpapp.dto.response.LeaderboardResponse;
import com.sankalpapp.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping("/exam/{examId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<LeaderboardResponse>> getExamLeaderboard(
            @PathVariable Long examId
    ) {

        return ResponseEntity.ok(
                leaderboardService.getExamLeaderboard(examId)
        );
    }

    @GetMapping("/test-series/{testSeriesId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<LeaderboardResponse>> getTestSeriesLeaderboard(
            @PathVariable Long testSeriesId
    ) {

        return ResponseEntity.ok(
                leaderboardService.getTestSeriesLeaderboard(
                        testSeriesId
                )
        );
    }

    @GetMapping("/exam/{examId}/pdf")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<byte[]> getExamLeaderboardPdf(
            @PathVariable Long examId
    ) {

        byte[] pdf =
                leaderboardService
                        .generateExamLeaderboardPdf(
                                examId
                        );

        return ResponseEntity.ok()
                .header(
                        "Content-Disposition",
                        "attachment; filename=exam-leaderboard.pdf"
                )
                .header(
                        "Content-Type",
                        "application/pdf"
                )
                .body(pdf);
    }

    @PostMapping("/exam/{examId}/finalize")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> finalizeExamLeaderboard(
            @PathVariable Long examId
    ) {

        leaderboardService.finalizeExamLeaderboard(examId);

        return ResponseEntity.ok(
                "Exam leaderboard finalized successfully"
        );
    }

    @PostMapping("/test-series/{testSeriesId}/finalize")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String>
    finalizeTestSeriesLeaderboard(@PathVariable Long testSeriesId) {
        leaderboardService.finalizeTestSeriesLeaderboard(testSeriesId);
        return ResponseEntity.ok(
                "Test series leaderboard calculated successfully"
        );
    }
}