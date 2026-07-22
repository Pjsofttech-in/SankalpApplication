package com.testapplication.controller;

import com.testapplication.dto.Request.ResultRequest;
import com.testapplication.dto.Response.ResultResponse;
import com.testapplication.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public ResultResponse saveResult(@RequestBody ResultRequest request) {
        return resultService.saveResult(request);
    }

    // Get All Results
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public List<ResultResponse> getAllResults() {
        return resultService.getAllResults();
    }

    // Get Result By Id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public ResultResponse getResultById(@PathVariable Long id) {
        return resultService.getResultById(id);
    }

    // Update Result
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public ResultResponse updateResult(@PathVariable Long id,
                                       @RequestBody ResultRequest request) {
        return resultService.updateResult(id, request);
    }

    // Delete Result
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteResult(@PathVariable Long id) {

        resultService.deleteResult(id);

        return "Result deleted successfully.";
    }
}