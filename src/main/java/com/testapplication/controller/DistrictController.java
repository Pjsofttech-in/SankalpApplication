package com.testapplication.controller;

import com.testapplication.entity.District;
import com.testapplication.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/districts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DistrictController {

    private final DistrictService districtService;

    // Save District
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public District saveDistrict(@RequestBody District district) {

        return districtService.saveDistrict(district);
    }

    // Get All Districts
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public List<District> getAllDistricts() {

        return districtService.getAllDistricts();
    }

    // Get District By Id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public District getDistrictById(@PathVariable Long id) {

        return districtService.getDistrictById(id);
    }

    // Update District
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public District updateDistrict(@PathVariable Long id,
                                   @RequestBody District district) {

        return districtService.updateDistrict(id, district);
    }

    // Delete District
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteDistrict(@PathVariable Long id) {

        districtService.deleteDistrict(id);

        return "District Deleted Successfully";
    }
}