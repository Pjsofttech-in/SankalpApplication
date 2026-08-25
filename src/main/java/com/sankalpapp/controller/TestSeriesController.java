package com.sankalpapp.controller;

import com.sankalpapp.dto.Request.TestSeriesExamRequest;
import com.sankalpapp.dto.Request.TestSeriesRequest;
import com.sankalpapp.dto.Response.TestSeriesProgressResponse;
import com.sankalpapp.dto.Response.TestSeriesResponse;
import com.sankalpapp.service.TestSeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-series")
@RequiredArgsConstructor
public class TestSeriesController {

    private final TestSeriesService testSeriesService;

    @PostMapping
    public ResponseEntity<TestSeriesResponse> create(
            @RequestBody TestSeriesRequest request
    ) {

        return ResponseEntity.ok(
                testSeriesService.create(request)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestSeriesResponse> update(
            @PathVariable Long id,
            @RequestBody TestSeriesRequest request
    ) {

        return ResponseEntity.ok(
                testSeriesService.update(id, request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestSeriesResponse> getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                testSeriesService.getById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<TestSeriesResponse>> getAll() {

        return ResponseEntity.ok(
                testSeriesService.getAll()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id
    ) {

        testSeriesService.delete(id);

        return ResponseEntity.ok(
                "Test Series deleted successfully"
        );
    }

    @PostMapping("/{testSeriesId}/exams")
    public ResponseEntity<TestSeriesResponse> addExam(
            @PathVariable Long testSeriesId,
            @RequestBody TestSeriesExamRequest request
    ) {

        return ResponseEntity.ok(
                testSeriesService.addExam(
                        testSeriesId,
                        request
                )
        );
    }

    @DeleteMapping("/{testSeriesId}/exams/{examId}")
    public ResponseEntity<TestSeriesResponse> removeExam(
            @PathVariable Long testSeriesId,
            @PathVariable Long examId
    ) {

        return ResponseEntity.ok(
                testSeriesService.removeExam(
                        testSeriesId,
                        examId
                )
        );
    }

    @GetMapping("/{testSeriesId}/progress/{studentId}")
    public ResponseEntity<TestSeriesProgressResponse> getStudentProgress(
            @PathVariable Long testSeriesId,
            @PathVariable Long studentId
    ) {

        return ResponseEntity.ok(
                testSeriesService.getStudentProgress(
                        testSeriesId,
                        studentId
                )
        );
    }
}