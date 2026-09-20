package com.sankalpapp.controller;

import com.sankalpapp.dto.request.CoordinatorRequest;
import com.sankalpapp.dto.response.CoordinatorDTO;
import com.sankalpapp.service.CoordinatorService;
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
    public CoordinatorDTO saveCoordinator(@RequestBody CoordinatorRequest request) {

        return coordinatorService.saveCoordinator(request);
    }

    // Get All Coordinators
    @GetMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public List<CoordinatorDTO> getAllCoordinators() {

        return coordinatorService.getAllCoordinators();
    }

    // Get Coordinator By Id
    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public CoordinatorDTO getCoordinatorById(@PathVariable Long id) {

        return coordinatorService.getCoordinatorById(id);
    }

    // Get Coordinator By Center Id
    @GetMapping("/center/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public List<CoordinatorDTO> getCoordinatorByCenterId(@PathVariable Long id) {

        return coordinatorService.getCoordinatorByCenter(id);
    }

    // Update Coordinator
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CoordinatorDTO updateCoordinator(@PathVariable Long id,
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