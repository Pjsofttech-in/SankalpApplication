package com.testapplication.controller;

import com.testapplication.dto.Request.CoordinatorRequest;
import com.testapplication.dto.Response.CoordinatorResponse;
import com.testapplication.service.CoordinatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public CoordinatorResponse saveCoordinator(@RequestBody CoordinatorRequest request) {

        return coordinatorService.saveCoordinator(request);
    }

    // Get All Coordinators
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public List<CoordinatorResponse> getAllCoordinators() {

        return coordinatorService.getAllCoordinators();
    }

    // Get Coordinator By Id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public CoordinatorResponse getCoordinatorById(@PathVariable Long id) {

        return coordinatorService.getCoordinatorById(id);
    }

    // Update Coordinator
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CoordinatorResponse updateCoordinator(@PathVariable Long id,
                                                 @RequestBody CoordinatorRequest request) {

        return coordinatorService.updateCoordinator(id, request);
    }

    // Delete Coordinator
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteCoordinator(@PathVariable Long id) {

        coordinatorService.deleteCoordinator(id);

        return "Coordinator deleted successfully.";
    }
}