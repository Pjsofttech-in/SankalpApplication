package com.testapplication.controller;

import com.testapplication.entity.Taluka;
import com.testapplication.service.TalukaService;
import lombok.RequiredArgsConstructor;
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
    public Taluka saveTaluka(@RequestBody Taluka taluka) {
        return talukaService.saveTaluka(taluka);
    }

    // Get All Talukas
    @GetMapping
    public List<Taluka> getAllTalukas() {
        return talukaService.getAllTalukas();
    }

    // Get Talukas By District
    @GetMapping("/district/{districtId}")
    public List<Taluka> getTalukasByDistrict(@PathVariable Long districtId) {
        return talukaService.getTalukasByDistrict(districtId);
    }

    // Get Taluka By Id
    @GetMapping("/{id}")
    public Taluka getTalukaById(@PathVariable Long id) {
        return talukaService.getTalukaById(id);
    }

    // Update Taluka
    @PutMapping("/{id}")
    public Taluka updateTaluka(@PathVariable Long id,
                               @RequestBody Taluka taluka) {

        return talukaService.updateTaluka(id, taluka);
    }

    // Delete Taluka
    @DeleteMapping("/{id}")
    public String deleteTaluka(@PathVariable Long id) {

        talukaService.deleteTaluka(id);

        return "Taluka Deleted Successfully";
    }
}