package com.sankalpapp.dynamicProfile.controller;

import com.sankalpapp.dynamicProfile.entity.WebFacultyTitle;
import com.sankalpapp.dynamicProfile.service.FacultyTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FacultyTitleController {

    @Autowired
    private FacultyTitleService service;

    @PostMapping("/createFacilityTitle")
    public ResponseEntity<WebFacultyTitle> createFacilityTitle(@RequestBody WebFacultyTitle webFacultyTitle,
                                                               @RequestParam String url) {
        return ResponseEntity.ok(service.createFacilityTitle(webFacultyTitle, url));
    }

    @GetMapping("/getAllFacilityTitles")
    public ResponseEntity<List<WebFacultyTitle>> getAllFacilityTitlesByBranchCode(@RequestParam String url) {
        return ResponseEntity.ok(service.getAllFacilityTitlesByBranchCode(url));
    }

    @GetMapping("/getFacilityTitleById/{id}")
    public ResponseEntity<WebFacultyTitle> getFacilityTitleById(@PathVariable Long id,
                                                                @RequestParam String url) {
        return ResponseEntity.ok(service.getFacilityTitleById(id, url));
    }

    @PutMapping("/updateFacilityTitle/{id}")
    public ResponseEntity<WebFacultyTitle> updateFacilityTitle(@PathVariable Long id,
                                                               @RequestBody WebFacultyTitle webFacultyTitle,
                                                               @RequestParam String url) {
        return ResponseEntity.ok(service.updateFacilityTitle(id, webFacultyTitle, url));
    }

    @DeleteMapping("/deleteFacilityTitle/{id}")
    public ResponseEntity<String> deleteFacilityTitle(@PathVariable Long id,
                                                      @RequestParam String url) {
        service.deleteFacilityTitle(id, url);
        return ResponseEntity.ok("Facility title deleted successfully");
    }
}
