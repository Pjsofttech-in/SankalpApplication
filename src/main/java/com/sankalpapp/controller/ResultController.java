package com.sankalpapp.controller;

import com.sankalpapp.dto.response.ExamResultResponse;
import com.sankalpapp.service.ExamAttemptService;
import com.sankalpapp.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultController {

    private final ExamAttemptService examAttemptService;
    private final ResultService resultService;

    @PutMapping("/{resultId}/publish")
    public ResponseEntity<ExamResultResponse>
    publishResult(
            @PathVariable Long resultId
    ) {

        return ResponseEntity.ok(
                examAttemptService.publishResult(
                        resultId
                )
        );
    }

    @PutMapping("/publishAllResults")
    public ResponseEntity<List<ExamResultResponse>> publishResultAll(
            @RequestBody List<Long> resultIds
    ) {

        return ResponseEntity.ok(
                examAttemptService.publishAllResults(
                        resultIds
                )
        );
    }

    // Get result
    @GetMapping("/{resultId}")
    public ResponseEntity<ExamResultResponse> getResult(
            @PathVariable Long resultId
    ) {

        return ResponseEntity.ok(
                examAttemptService.getResult(resultId)
        );
    }

    // Get result
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ExamResultResponse>> getAllResults() {
        return ResponseEntity.ok(resultService.getAllResults());
    }
}