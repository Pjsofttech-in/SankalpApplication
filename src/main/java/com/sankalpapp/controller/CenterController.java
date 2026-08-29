package com.sankalpapp.controller;

import com.sankalpapp.dto.request.CenterRequest;
import com.sankalpapp.dto.response.CenterDTO;
import com.sankalpapp.service.CenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/centers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CenterController {

    private final CenterService centerService;

    // Save Center
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public CenterDTO saveCenter(@RequestBody CenterRequest request) {

        return centerService.saveCenter(request);
    }

    // Get All Centers
    @GetMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public List<CenterDTO> getAllCenters() {

        return centerService.getAllCenters();
    }

    // Get Centers By Taluka (Dynamic Dropdown)
    @GetMapping("/taluka/{talukaId}")
//    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public List<CenterDTO> getCentersByTaluka(@PathVariable Long talukaId) {

        return centerService.getCentersByTaluka(talukaId);
    }

    @GetMapping("/schools/{centerId}")
//    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public List<String> getSchoolsByCenter(@PathVariable Long centerId) {
        return centerService.getSchoolsByCenter(centerId);
    }

    // Get Center By Id
    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public CenterDTO getCenterById(@PathVariable Long id) {

        return centerService.getCenterById(id);
    }

    // Update Center
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CenterDTO updateCenter(@PathVariable Long id,
                                  @RequestBody CenterRequest request) {

        return centerService.updateCenter(id, request);
    }

    // Delete Center
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteCenter(@PathVariable Long id) {

        centerService.deleteCenter(id);

        return "Center deleted successfully.";
    }
}