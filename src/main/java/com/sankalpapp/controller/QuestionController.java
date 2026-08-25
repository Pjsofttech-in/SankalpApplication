package com.sankalpapp.controller;

import com.sankalpapp.entity.Question;
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
    public ResponseEntity<Question> create(
            @RequestBody Question question
    ) {

        return ResponseEntity.ok(
                questionService.create(question)
        );
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAll() {

        return ResponseEntity.ok(
                questionService.getAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                questionService.getById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> update(
            @PathVariable Long id,
            @RequestBody Question question
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