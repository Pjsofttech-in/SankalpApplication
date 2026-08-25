package com.sankalpapp.controller;

import com.sankalpapp.dto.Request.ExamRequest;
import com.sankalpapp.dto.Request.SubmitExamRequest;
import com.sankalpapp.dto.Response.ExamResponse;
import com.sankalpapp.dto.Response.StartExamResponse;
import com.sankalpapp.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public ExamResponse saveExam(@RequestBody ExamRequest request) {
        return examService.saveExam(request);
    }

    // Get All Exams
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public List<ExamResponse> getAllExams() {
        return examService.getAllExams();
    }

    // Get Exam By Id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public ExamResponse getExamById(@PathVariable Long id) {
        return examService.getExamById(id);
    }

    // Update Exam
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public ExamResponse updateExam(@PathVariable Long id,
                                   @RequestBody ExamRequest request) {
        return examService.updateExam(id, request);
    }

    // Delete Exam
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteExam(@PathVariable Long id) {

        examService.deleteExam(id);

        return "Exam deleted successfully.";
    }

}