package com.testapplication.controller;

import com.testapplication.entity.Exam;
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

    // Save Exam
    @PostMapping
    public Exam saveExam(@RequestBody Exam exam) {
        return examService.saveExam(exam);
    }

    // Get All Exams
    @GetMapping
    public List<Exam> getAllExams() {
        return examService.getAllExams();
    }

    // Get Exam By Id
    @GetMapping("/{id}")
    public Exam getExamById(@PathVariable Long id) {
        return examService.getExamById(id);
    }

    // Update Exam
    @PutMapping("/{id}")
    public Exam updateExam(@PathVariable Long id,
                           @RequestBody Exam exam) {
        return examService.updateExam(id, exam);
    }

    // Delete Exam
    @DeleteMapping("/{id}")
    public String deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return "Exam deleted successfully.";
    }
}