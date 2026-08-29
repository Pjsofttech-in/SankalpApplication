package com.sankalpapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankalpapp.dto.Request.ExamRequest;
import com.sankalpapp.dto.Response.ExamResponse;
import com.sankalpapp.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExamController {

    private final ExamService examService;
    private final ObjectMapper objectMapper;

    // Save Exam
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public ExamResponse saveExam(@RequestPart("exam") String examJson,
                                 @RequestParam("examImage") MultipartFile image) throws JsonProcessingException {
        ExamRequest request = objectMapper.readValue(examJson, ExamRequest.class);
        return examService.saveExam(request, image);
    }

    // Get All Exams
    @GetMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public List<ExamResponse> getAllExams() {
        return examService.getAllExams();
    }

    // Get Exam By Id
    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public ExamResponse getExamById(@PathVariable Long id) {
        return examService.getExamById(id);
    }

    // Update Exam
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public ExamResponse updateExam(@PathVariable Long id,
                                   @RequestPart("exam") String examJson,
                                   @RequestParam("examImage") MultipartFile image) throws JsonProcessingException {
        ExamRequest request = objectMapper.readValue(examJson, ExamRequest.class);
        return examService.updateExam(id, request, image);
    }

    // Delete Exam
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteExam(@PathVariable Long id) {

        examService.deleteExam(id);

        return "Exam deleted successfully.";
    }

}