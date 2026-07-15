package com.testapplication.controller;

import com.testapplication.entity.Question;
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

    // Save Question
    @PostMapping
    public Question saveQuestion(@RequestBody Question question) {
        return questionService.saveQuestion(question);
    }

    // Get All Questions
    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    // Get Question By Id
    @GetMapping("/{id}")
    public Question getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }

    // Update Question
    @PutMapping("/{id}")
    public Question updateQuestion(@PathVariable Long id,
                                   @RequestBody Question question) {
        return questionService.updateQuestion(id, question);
    }

    // Delete Question
    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return "Question deleted successfully.";
    }
}