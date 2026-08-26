package com.sankalpapp.controller;

import com.sankalpapp.dto.Request.QuestionRequest;
import com.sankalpapp.dto.Response.QuestionResponse;
import com.sankalpapp.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(
            QuestionService questionService
    ) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<QuestionResponse> create(
            @RequestBody QuestionRequest questionRequest
    ) {

        return ResponseEntity.ok(
                questionService.create(questionRequest)
        );
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getAll() {

        return ResponseEntity.ok(
                questionService.getAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                questionService.getById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponse> update(
            @PathVariable Long id,
            @RequestBody QuestionRequest question
    ) {

        return ResponseEntity.ok(
                questionService.update(id, question)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id
    ) {

        questionService.delete(id);

        return ResponseEntity.ok(
                "Question deleted successfully"
        );
    }
}