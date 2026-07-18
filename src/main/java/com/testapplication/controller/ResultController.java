package com.testapplication.controller;

import com.testapplication.dto.Request.ResultRequest;
import com.testapplication.dto.Response.ResultResponse;
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

    @PostMapping
    public ResultResponse saveResult(@RequestBody ResultRequest request) {
        return resultService.saveResult(request);
    }

    @GetMapping
    public List<ResultResponse> getAllResults() {
        return resultService.getAllResults();
    }

    @GetMapping("/{id}")
    public ResultResponse getResultById(@PathVariable Long id) {
        return resultService.getResultById(id);
    }

    @PutMapping("/{id}")
    public ResultResponse updateResult(@PathVariable Long id,
                                       @RequestBody ResultRequest request) {
        return resultService.updateResult(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteResult(@PathVariable Long id) {

        resultService.deleteResult(id);

        return "Result deleted successfully.";
    }
}