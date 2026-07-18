package com.testapplication.controller;

import com.testapplication.dto.Request.QuestionRequest;
import com.testapplication.dto.Response.QuestionResponse;
import com.testapplication.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class QuestionController {

    private final QuestionService questionService;

    // Save
    @PostMapping
    public QuestionResponse saveQuestion(@RequestBody QuestionRequest request) {
        return questionService.saveQuestion(request);
    }

    // Get All
    @GetMapping
    public List<QuestionResponse> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    // Get By Id
    @GetMapping("/{id}")
    public QuestionResponse getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }

    // Update
    @PutMapping("/{id}")
    public QuestionResponse updateQuestion(@PathVariable Long id,
                                           @RequestBody QuestionRequest request) {
        return questionService.updateQuestion(id, request);
    }

    // Delete
    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id) {

        questionService.deleteQuestion(id);

        return "Question deleted successfully.";
    }
}