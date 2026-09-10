package com.sankalpapp.dynamicProfile.controller;

import com.sankalpapp.dynamicProfile.entity.ExamSection;
import com.sankalpapp.dynamicProfile.service.ExamSectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exam-section")
@RequiredArgsConstructor
public class ExamSectionController {

    private final ExamSectionService examSectionService;

    @GetMapping
    public ResponseEntity<ExamSection> getExamSection() {

        ExamSection examSection =
                examSectionService.getExamSection();

        if (examSection == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(examSection);
    }

    @PutMapping
    public ResponseEntity<ExamSection> updateExamSection(
            @RequestBody ExamSection examSection) {

        return ResponseEntity.ok(
                examSectionService.updateExamSection(examSection)
        );
    }
}