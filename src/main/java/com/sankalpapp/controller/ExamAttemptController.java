package com.sankalpapp.controller;

import com.sankalpapp.dto.Request.StudentAnswerRequest;
import com.sankalpapp.dto.Request.SubmitExamRequest;
import com.sankalpapp.dto.Response.ExamResultResponse;
import com.sankalpapp.dto.Response.ExamStartResponse;
import com.sankalpapp.dto.Response.StudentQuestionResponse;
import com.sankalpapp.service.ExamAttemptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam-attempts")
public class ExamAttemptController {

    private final ExamAttemptService examAttemptService;

    public ExamAttemptController(
            ExamAttemptService examAttemptService
    ) {
        this.examAttemptService =
                examAttemptService;
    }

    @PostMapping("/start")
    public ResponseEntity<ExamStartResponse> startExam(
            @RequestParam Long examId
    ) {

        return ResponseEntity.ok(
                examAttemptService.startExam(
                        examId
                )
        );
    }

    @GetMapping("/{attemptId}/questions")
    public ResponseEntity<List<StudentQuestionResponse>>
    getQuestions(
            @PathVariable Long attemptId
    ) {

        return ResponseEntity.ok(
                examAttemptService
                        .getExamQuestions(
                                attemptId
                        )
        );
    }

    @PostMapping("/{attemptId}/answers")
    public ResponseEntity<String> saveAnswer(
            @PathVariable Long attemptId,
            @RequestBody StudentAnswerRequest request
    ) {

        examAttemptService.saveAnswer(
                attemptId,
                request
        );

        return ResponseEntity.ok(
                "Answer saved successfully"
        );
    }

    @PostMapping("/{attemptId}/submit")
    public ResponseEntity<ExamResultResponse> submitExam(
            @PathVariable Long attemptId
    ) {

        return ResponseEntity.ok(
                examAttemptService.submitExam(attemptId)
        );
    }

    @GetMapping("/{attemptId}/result")
    public ResponseEntity<ExamResultResponse>
    getResult(
            @PathVariable Long attemptId
    ) {

        return ResponseEntity.ok(
                examAttemptService.getResult(
                        attemptId
                )
        );
    }
}