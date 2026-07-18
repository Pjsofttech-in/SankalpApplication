package com.testapplication.controller;

import com.testapplication.dto.Request.ExamRequest;
import com.testapplication.dto.Response.ExamResponse;
import com.testapplication.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExamController {

    private final ExamService examService;

    @PostMapping
    public ExamResponse saveExam(@RequestBody ExamRequest request) {
        return examService.saveExam(request);
    }

    @GetMapping
    public List<ExamResponse> getAllExams() {
        return examService.getAllExams();
    }

    @GetMapping("/{id}")
    public ExamResponse getExamById(@PathVariable Long id) {
        return examService.getExamById(id);
    }

    @PutMapping("/{id}")
    public ExamResponse updateExam(@PathVariable Long id,
                                   @RequestBody ExamRequest request) {
        return examService.updateExam(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return "Exam deleted successfully.";
    }
}