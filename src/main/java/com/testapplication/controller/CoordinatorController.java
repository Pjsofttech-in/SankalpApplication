package com.testapplication.controller;

import com.testapplication.entity.Coordinator;
import com.testapplication.service.CoordinatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coordinators")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CoordinatorController {

    private final CoordinatorService coordinatorService;

    // Save Coordinator
    @PostMapping
    public Coordinator saveCoordinator(@RequestBody Coordinator coordinator) {
        return coordinatorService.saveCoordinator(coordinator);
    }

    // Get All Coordinators
    @GetMapping
    public List<Coordinator> getAllCoordinators() {
        return coordinatorService.getAllCoordinators();
    }

    // Get Coordinator By Id
    @GetMapping("/{id}")
    public Coordinator getCoordinatorById(@PathVariable Long id) {
        return coordinatorService.getCoordinatorById(id);
    }

    // Update Coordinator
    @PutMapping("/{id}")
    public Coordinator updateCoordinator(@PathVariable Long id,
                                         @RequestBody Coordinator coordinator) {
        return coordinatorService.updateCoordinator(id, coordinator);
    }

    // Delete Coordinator
    @DeleteMapping("/{id}")
    public String deleteCoordinator(@PathVariable Long id) {
        coordinatorService.deleteCoordinator(id);
        return "Coordinator deleted successfully.";
    }
}