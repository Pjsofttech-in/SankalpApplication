package com.testapplication.controller;

import com.testapplication.entity.Result;
import com.testapplication.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ResultController {

    private final ResultService resultService;

    // Save Result
    @PostMapping
    public Result saveResult(@RequestBody Result result) {
        return resultService.saveResult(result);
    }

    // Get All Results
    @GetMapping
    public List<Result> getAllResults() {
        return resultService.getAllResults();
    }

    // Get Result By Id
    @GetMapping("/{id}")
    public Result getResultById(@PathVariable Long id) {
        return resultService.getResultById(id);
    }

    // Update Result
    @PutMapping("/{id}")
    public Result updateResult(@PathVariable Long id,
                               @RequestBody Result result) {
        return resultService.updateResult(id, result);
    }

    // Delete Result
    @DeleteMapping("/{id}")
    public String deleteResult(@PathVariable Long id) {
        resultService.deleteResult(id);
        return "Result deleted successfully.";
    }
}