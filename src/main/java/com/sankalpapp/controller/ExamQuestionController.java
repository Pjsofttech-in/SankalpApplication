package com.sankalpapp.controller;

import com.sankalpapp.dto.request.AddQuestionRequest;
import com.sankalpapp.dto.response.ExamQuestionDto;
import com.sankalpapp.service.ExamQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamQuestionController {

    private final ExamQuestionService examQuestionService;

    @PostMapping("/{examId}/questions")
    public ResponseEntity<ExamQuestionDto> addQuestion(
            @PathVariable Long examId,
            @RequestBody AddQuestionRequest request
    ) {
        return ResponseEntity.ok(
                examQuestionService.addQuestion(
                        examId,
                        request.getQuestionId(),
                        request.getSequence(),
                        request.getMarks()
                )
        );
    }

    @GetMapping("/{examId}/questions")
    public ResponseEntity<List<ExamQuestionDto>> getQuestions(
            @PathVariable Long examId
    ) {
        return ResponseEntity.ok(
                examQuestionService.getQuestionsByExam(examId)
        );
    }

    @DeleteMapping("/{examId}/questions/{questionId}")
    public ResponseEntity<String> removeQuestion(
            @PathVariable Long examId,
            @PathVariable Long questionId
    ) {
        examQuestionService.removeQuestion(examId, questionId);
        return ResponseEntity.ok("Question removed from exam successfully");
    }

    @PutMapping("/{examId}/questions/{questionId}/sequence")
    public ResponseEntity<ExamQuestionDto> updateSequence(
            @PathVariable Long examId,
            @PathVariable Long questionId,
            @RequestBody Integer sequence
    ) {
        return ResponseEntity.ok(
                examQuestionService.updateSequence(
                        examId,
                        questionId,
                        sequence
                )
        );
    }

    @PutMapping("/{examId}/questions/{questionId}/marks")
    public ResponseEntity<ExamQuestionDto> updateMarks(
            @PathVariable Long examId,
            @PathVariable Long questionId,
            @RequestBody Integer marks
    ) {
        return ResponseEntity.ok(
                examQuestionService.updateMarks(
                        examId,
                        questionId,
                        marks
                )
        );
    }
}