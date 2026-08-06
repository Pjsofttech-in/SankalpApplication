package com.sankalpapp.controller;

import com.sankalpapp.entity.Taluka;
import com.sankalpapp.service.TalukaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/talukas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TalukaController {

    private final TalukaService talukaService;

    // Save Taluka
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Taluka saveTaluka(@RequestBody Taluka taluka) {
        return talukaService.saveTaluka(taluka);
    }

    // Get All Talukas
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public List<Taluka> getAllTalukas() {
        return talukaService.getAllTalukas();
    }

    // Get Talukas By District
    @GetMapping("/district/{districtId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public List<Taluka> getTalukasByDistrict(@PathVariable Long districtId) {
        return talukaService.getTalukasByDistrict(districtId);
    }

    // Get Taluka By Id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public Taluka getTalukaById(@PathVariable Long id) {
        return talukaService.getTalukaById(id);
    }

    // Update Taluka
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Taluka updateTaluka(@PathVariable Long id,
                               @RequestBody Taluka taluka) {

        return talukaService.updateTaluka(id, taluka);
    }

    // Delete Taluka
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteTaluka(@PathVariable Long id) {

        talukaService.deleteTaluka(id);

        return "Taluka Deleted Successfully";
    }
}