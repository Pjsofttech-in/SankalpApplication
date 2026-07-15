package com.testapplication.controller;

import com.testapplication.entity.School;
import com.testapplication.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SchoolController {

    private final SchoolService schoolService;

    // Save School
    @PostMapping
    public School saveSchool(@RequestBody School school) {
        return schoolService.saveSchool(school);
    }

    // Get All Schools
    @GetMapping
    public List<School> getAllSchools() {
        return schoolService.getAllSchools();
    }

    // Get School By Id
    @GetMapping("/{id}")
    public School getSchoolById(@PathVariable Long id) {
        return schoolService.getSchoolById(id);
    }

    // Update School
    @PutMapping("/{id}")
    public School updateSchool(@PathVariable Long id,
                               @RequestBody School school) {
        return schoolService.updateSchool(id, school);
    }

    // Delete School
    @DeleteMapping("/{id}")
    public String deleteSchool(@PathVariable Long id) {
        schoolService.deleteSchool(id);
        return "School deleted successfully.";
    }
}