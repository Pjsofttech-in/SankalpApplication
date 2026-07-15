package com.testapplication.controller;

import com.testapplication.entity.AnswerKey;
import com.testapplication.service.AnswerKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answerkeys")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AnswerKeyController {

    private final AnswerKeyService answerKeyService;

    // Save Answer Key
    @PostMapping
    public AnswerKey saveAnswerKey(@RequestBody AnswerKey answerKey) {
        return answerKeyService.saveAnswerKey(answerKey);
    }

    // Get All Answer Keys
    @GetMapping
    public List<AnswerKey> getAllAnswerKeys() {
        return answerKeyService.getAllAnswerKeys();
    }

    // Get Answer Key By Id
    @GetMapping("/{id}")
    public AnswerKey getAnswerKeyById(@PathVariable Long id) {
        return answerKeyService.getAnswerKeyById(id);
    }

    // Update Answer Key
    @PutMapping("/{id}")
    public AnswerKey updateAnswerKey(@PathVariable Long id,
                                     @RequestBody AnswerKey answerKey) {
        return answerKeyService.updateAnswerKey(id, answerKey);
    }

    // Delete Answer Key
    @DeleteMapping("/{id}")
    public String deleteAnswerKey(@PathVariable Long id) {
        answerKeyService.deleteAnswerKey(id);
        return "Answer Key deleted successfully.";
    }
}