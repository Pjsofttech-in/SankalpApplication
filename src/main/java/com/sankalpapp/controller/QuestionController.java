package com.sankalpapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankalpapp.dto.request.QuestionRequest;
import com.sankalpapp.dto.response.QuestionResponse;
import com.sankalpapp.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<QuestionResponse> create(
            @RequestParam(required = false) String questionRequestJson,
            @RequestPart(required = false) MultipartFile createQuestionFile,
            @RequestPart(required = false) MultipartFile optionAFile,
            @RequestPart(required = false) MultipartFile optionBFile,
            @RequestPart(required = false) MultipartFile optionCFile,
            @RequestPart(required = false) MultipartFile optionDFile,
            @RequestPart(required = false) MultipartFile answerSupportingFile
    ) throws JsonProcessingException {
        QuestionRequest questionRequest = objectMapper.readValue(questionRequestJson, QuestionRequest.class);
        return ResponseEntity.ok(
                questionService.create(questionRequest, createQuestionFile, optionAFile,
                        optionBFile, optionCFile, optionDFile, answerSupportingFile)
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
            @RequestParam(required = false) String questionRequestJson,
            @RequestParam(required = false) MultipartFile createQuestionFile,
            @RequestParam(required = false) MultipartFile optionAFile,
            @RequestParam(required = false) MultipartFile optionBFile,
            @RequestParam(required = false) MultipartFile optionCFile,
            @RequestParam(required = false) MultipartFile optionDFile,
            @RequestParam(required = false) MultipartFile answerSupportingFile
    ) throws JsonProcessingException {
        QuestionRequest questionRequest = objectMapper.readValue(questionRequestJson, QuestionRequest.class);
        return ResponseEntity.ok(
                questionService.update(id, questionRequest, createQuestionFile, optionAFile,
                        optionBFile, optionCFile, optionDFile, answerSupportingFile)
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