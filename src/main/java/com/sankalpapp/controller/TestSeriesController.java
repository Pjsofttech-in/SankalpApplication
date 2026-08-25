package com.sankalpapp.controller;

import com.sankalpapp.dto.Request.AddExamToTestSeriesRequest;
import com.sankalpapp.dto.Request.CreateTestSeriesRequest;
import com.sankalpapp.dto.Request.ReorderExamRequest;
import com.sankalpapp.entity.TestSeries;
import com.sankalpapp.service.TestSeriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-series")
public class TestSeriesController {

    private final TestSeriesService testSeriesService;

    public TestSeriesController(
            TestSeriesService testSeriesService
    ) {
        this.testSeriesService = testSeriesService;
    }

    @PostMapping
    public ResponseEntity<TestSeries> create(
            @RequestBody CreateTestSeriesRequest request
    ) {

        return ResponseEntity.ok(
                testSeriesService.create(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<TestSeries>> getAll() {

        return ResponseEntity.ok(
                testSeriesService.getAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestSeries> getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                testSeriesService.getById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestSeries> update(
            @PathVariable Long id,
            @RequestBody CreateTestSeriesRequest request
    ) {

        return ResponseEntity.ok(
                testSeriesService.update(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id
    ) {

        testSeriesService.delete(id);

        return ResponseEntity.ok(
                "Test series deleted successfully"
        );
    }

    @PostMapping("/{testSeriesId}/exams")
    public ResponseEntity<String> addExam(
            @PathVariable Long testSeriesId,
            @RequestBody AddExamToTestSeriesRequest request
    ) {

        testSeriesService.addExam(
                testSeriesId,
                request
        );

        return ResponseEntity.ok(
                "Exam added to test series successfully"
        );
    }

    @DeleteMapping("/{testSeriesId}/exams/{examId}")
    public ResponseEntity<String> removeExam(
            @PathVariable Long testSeriesId,
            @PathVariable Long examId
    ) {

        testSeriesService.removeExam(
                testSeriesId,
                examId
        );

        return ResponseEntity.ok(
                "Exam removed from test series successfully"
        );
    }

    @PutMapping("/{testSeriesId}/exams/{examId}/sequence")
    public ResponseEntity<String> reorderExam(
            @PathVariable Long testSeriesId,
            @PathVariable Long examId,
            @RequestBody ReorderExamRequest request
    ) {

        testSeriesService.reorderExam(
                testSeriesId,
                examId,
                request
        );

        return ResponseEntity.ok(
                "Exam sequence updated successfully"
        );
    }
}