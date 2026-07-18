package com.testapplication.controller;

import com.testapplication.dto.Request.CoordinatorRequest;
import com.testapplication.dto.Response.CoordinatorResponse;
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

    @PostMapping
    public CoordinatorResponse saveCoordinator(@RequestBody CoordinatorRequest request) {
        return coordinatorService.saveCoordinator(request);
    }

    @GetMapping
    public List<CoordinatorResponse> getAllCoordinators() {
        return coordinatorService.getAllCoordinators();
    }

    @GetMapping("/{id}")
    public CoordinatorResponse getCoordinatorById(@PathVariable Long id) {
        return coordinatorService.getCoordinatorById(id);
    }

    @PutMapping("/{id}")
    public CoordinatorResponse updateCoordinator(@PathVariable Long id,
                                                 @RequestBody CoordinatorRequest request) {
        return coordinatorService.updateCoordinator(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteCoordinator(@PathVariable Long id) {
        coordinatorService.deleteCoordinator(id);
        return "Coordinator deleted successfully.";
    }
}