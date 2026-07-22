package com.testapplication.controller;

import com.testapplication.dto.Request.QuestionRequest;
import com.testapplication.dto.Response.QuestionResponse;
import com.testapplication.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class QuestionController {

    private final QuestionService questionService;

    // Save Question
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public QuestionResponse saveQuestion(@RequestBody QuestionRequest request) {
        return questionService.saveQuestion(request);
    }

    // Get All Questions
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public List<QuestionResponse> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    // Get Question By Id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public QuestionResponse getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }

    // Update Question
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public QuestionResponse updateQuestion(@PathVariable Long id,
                                           @RequestBody QuestionRequest request) {
        return questionService.updateQuestion(id, request);
    }

    // Delete Question
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteQuestion(@PathVariable Long id) {

        questionService.deleteQuestion(id);

        return "Question deleted successfully.";
    }
}