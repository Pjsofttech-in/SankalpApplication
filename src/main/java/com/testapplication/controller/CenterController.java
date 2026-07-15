package com.testapplication.controller;

import com.testapplication.entity.Center;
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
    public Center saveCenter(@RequestBody Center center) {
        return centerService.saveCenter(center);
    }

    // Get All Centers
    @GetMapping
    public List<Center> getAllCenters() {
        return centerService.getAllCenters();
    }

    // Get Center By Id
    @GetMapping("/{id}")
    public Center getCenterById(@PathVariable Long id) {
        return centerService.getCenterById(id);
    }

    // Update Center
    @PutMapping("/{id}")
    public Center updateCenter(@PathVariable Long id,
                               @RequestBody Center center) {
        return centerService.updateCenter(id, center);
    }

    // Delete Center
    @DeleteMapping("/{id}")
    public String deleteCenter(@PathVariable Long id) {
        centerService.deleteCenter(id);
        return "Center deleted successfully.";
    }
}