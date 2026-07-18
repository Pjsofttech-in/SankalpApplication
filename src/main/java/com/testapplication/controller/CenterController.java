package com.testapplication.controller;

import com.testapplication.dto.Request.CenterRequest;
import com.testapplication.dto.Response.CenterResponse;
import com.testapplication.service.CenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/centers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CenterController {

    private final CenterService centerService;

    // Save Center
    @PostMapping
    public CenterResponse saveCenter(@RequestBody CenterRequest request) {
        return centerService.saveCenter(request);
    }

    // Get All Centers
    @GetMapping
    public List<CenterResponse> getAllCenters() {
        return centerService.getAllCenters();
    }

    // Get Centers By Taluka (Dynamic Dropdown)
    @GetMapping("/taluka/{talukaId}")
    public List<CenterResponse> getCentersByTaluka(@PathVariable Long talukaId) {
        return centerService.getCentersByTaluka(talukaId);
    }

    // Get Center By Id
    @GetMapping("/{id}")
    public CenterResponse getCenterById(@PathVariable Long id) {
        return centerService.getCenterById(id);
    }

    // Update Center
    @PutMapping("/{id}")
    public CenterResponse updateCenter(@PathVariable Long id,
                                       @RequestBody CenterRequest request) {
        return centerService.updateCenter(id, request);
    }

    // Delete Center
    @DeleteMapping("/{id}")
    public String deleteCenter(@PathVariable Long id) {
        centerService.deleteCenter(id);
        return "Center deleted successfully.";
    }
}