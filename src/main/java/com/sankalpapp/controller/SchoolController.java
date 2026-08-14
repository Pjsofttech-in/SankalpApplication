package com.sankalpapp.controller;

import com.sankalpapp.dto.Response.SchoolResponse;
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
    public SchoolResponse saveSchool(@RequestBody School school) {
        return schoolService.saveSchool(school);
    }

    // Get All Schools
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public List<SchoolResponse> getAllSchools() {
        return schoolService.getAllSchools();
    }

    // Get School By Id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public SchoolResponse getSchoolById(@PathVariable Long id) {
        return schoolService.getSchoolById(id);
    }

    @GetMapping("/center/{id}/{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public List<SchoolResponse> getSchoolByCenterIdOrName(@PathVariable Long id, @PathVariable String name) {
        return schoolService.getSchoolByCenterIdOrName(id, name);
    }

    // Update School
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SchoolResponse updateSchool(@PathVariable Long id,
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