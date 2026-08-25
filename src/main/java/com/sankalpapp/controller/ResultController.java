package com.sankalpapp.controller;

import com.sankalpapp.dto.Response.ExamResultResponse;
import com.sankalpapp.service.ExamAttemptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    private final ExamAttemptService examAttemptService;

    public ResultController(
            ExamAttemptService examAttemptService
    ) {
        this.examAttemptService =
                examAttemptService;
    }

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

    // Get result
    @GetMapping("/{resultId}")
    public ResponseEntity<ExamResultResponse> getResult(
            @PathVariable Long resultId
    ) {

        return ResponseEntity.ok(
                examAttemptService.getResult(resultId)
        );
    }
}