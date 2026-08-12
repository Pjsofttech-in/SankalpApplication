package com.sankalpapp.controller;

import com.sankalpapp.entity.TestSeries;
import com.sankalpapp.service.TestSeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
//    @CrossOrigin("https://mahastudy.in")
    @CrossOrigin(origins = {
            "http://localhost:5173",
            "http://localhost:5174",
            "https://mahastudy.in"
    })
    @RequiredArgsConstructor
    public class TestSeriesController {

        private final TestSeriesService testSeriesService;

        // Create
        @PostMapping("/createTestSeries")
        public ResponseEntity<TestSeries> create(@RequestBody TestSeries testSeries) {
            return ResponseEntity.ok(testSeriesService.createTestSeries(testSeries));
        }

        //  Update
        @PutMapping("/UpdateTestSeriesById/{id}")
        public ResponseEntity<TestSeries> update(
                @PathVariable Long id,
                @RequestBody TestSeries testSeries) {
            return ResponseEntity.ok(testSeriesService.updateTestSeries(id, testSeries));
        }

        //  Get All
        @GetMapping("/getAllTestSeries")
        public ResponseEntity<List<TestSeries>> getAll() {
            return ResponseEntity.ok(testSeriesService.getAllTestSeries());
        }

        //  Get By Id
        @GetMapping("/GetById/{id}")
        public ResponseEntity<TestSeries> getById(@PathVariable Long id) {
            return ResponseEntity.ok(testSeriesService.getTestSeriesById(id));
        }

        //  Delete
        @DeleteMapping("/deleteTestSeries/{id}")
        public ResponseEntity<String> delete(@PathVariable Long id) {
            testSeriesService.deleteTestSeries(id);
            return ResponseEntity.ok("TestSeries deleted successfully");
        }
    }


