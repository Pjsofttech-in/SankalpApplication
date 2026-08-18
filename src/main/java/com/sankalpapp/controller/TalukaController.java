package com.sankalpapp.controller;

import com.sankalpapp.dto.Request.TalukaRequest;
import com.sankalpapp.dto.Response.TalukaDTO;
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
    public TalukaDTO saveTaluka(@RequestBody TalukaRequest request) {
        return talukaService.saveTaluka(request);
    }

    // Get All Talukas
    @GetMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public List<TalukaDTO> getAllTalukas() {
        return talukaService.getAllTalukas();
    }

    // Get Talukas By District
    @GetMapping("/district/{districtId}")
//    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public List<TalukaDTO> getTalukasByDistrict(@PathVariable Long districtId) {
        return talukaService.getTalukasByDistrict(districtId);
    }

    // Get Taluka By Id
    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public TalukaDTO getTalukaById(@PathVariable Long id) {
        return talukaService.getTalukaById(id);
    }

    // Update Taluka
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public TalukaDTO updateTaluka(@PathVariable Long id,
                                  @RequestBody TalukaRequest request) {

        return talukaService.updateTaluka(id, request);
    }

    // Delete Taluka
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteTaluka(@PathVariable Long id) {
        talukaService.deleteTaluka(id);
        return "Taluka Deleted Successfully";
    }
}