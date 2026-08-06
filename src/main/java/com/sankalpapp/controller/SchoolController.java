package com.sankalpapp.controller;

import com.sankalpapp.entity.School;
import com.sankalpapp.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public School saveSchool(@RequestBody School school) {
        return schoolService.saveSchool(school);
    }

    // Get All Schools
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public List<School> getAllSchools() {
        return schoolService.getAllSchools();
    }

    // Get School By Id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public School getSchoolById(@PathVariable Long id) {
        return schoolService.getSchoolById(id);
    }

    // Update School
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public School updateSchool(@PathVariable Long id,
                               @RequestBody School school) {
        return schoolService.updateSchool(id, school);
    }

    // Delete School
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteSchool(@PathVariable Long id) {
        schoolService.deleteSchool(id);
        return "School Deleted Successfully";
    }
}