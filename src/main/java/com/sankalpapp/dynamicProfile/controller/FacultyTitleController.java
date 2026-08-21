package com.sankalpapp.dynamicProfile.controller;

import com.sankalpapp.dynamicProfile.entity.WebFacultyTitle;
import com.sankalpapp.dynamicProfile.service.FacultyTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class FacultyTitleController {

    @Autowired
    private FacultyTitleService service;

    @PostMapping("/createFacilityTitle")
    public ResponseEntity<WebFacultyTitle> createFacilityTitle(@RequestBody WebFacultyTitle webFacultyTitle,
                                                               @RequestParam String role,
                                                               @RequestParam String email,
                                                               @RequestParam String url) {
        return ResponseEntity.ok(service.createFacilityTitle(webFacultyTitle, role, email, url));
    }

    @GetMapping("/getAllFacilityTitles")
    public ResponseEntity<List<WebFacultyTitle>> getAllFacilityTitlesByBranchCode(@RequestParam String role,
                                                                                  @RequestParam(required = false) String email,
                                                                                  @RequestParam String url,
                                                                                  @RequestParam String branchCode) {
        return ResponseEntity.ok(service.getAllFacilityTitlesByBranchCode(role, email, url, branchCode));
    }

    @GetMapping("/getFacilityTitleById/{id}")
    public ResponseEntity<WebFacultyTitle> getFacilityTitleById(@PathVariable Long id,
                                                                @RequestParam String role,
                                                                @RequestParam String email,
                                                                @RequestParam String url) {
        return ResponseEntity.ok(service.getFacilityTitleById(id, role, email, url));
    }

    @PutMapping("/updateFacilityTitle/{id}")
    public ResponseEntity<WebFacultyTitle> updateFacilityTitle(@PathVariable Long id,
                                                               @RequestBody WebFacultyTitle webFacultyTitle,
                                                               @RequestParam String role,
                                                               @RequestParam String email,
                                                               @RequestParam String url) {
        return ResponseEntity.ok(service.updateFacilityTitle(id, webFacultyTitle, role, email, url));
    }

    @DeleteMapping("/deleteFacilityTitle/{id}")
    public ResponseEntity<String> deleteFacilityTitle(@PathVariable Long id,
                                                      @RequestParam String role,
                                                      @RequestParam String email,
                                                      @RequestParam String url) {
        service.deleteFacilityTitle(id, role, email, url);
        return ResponseEntity.ok("Facility title deleted successfully");
    }
}
