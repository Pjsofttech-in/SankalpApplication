package com.sankalpapp.dynamicProfile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankalpapp.dynamicProfile.entity.WebVisionMission;
import com.sankalpapp.dynamicProfile.service.VisionMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class VisionMissionController {

    @Autowired
    private VisionMissionService service;

    @PostMapping(value = "/createVisionMission", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebVisionMission> createVisionMission(
            @RequestPart("vm") String vmJson,
            @RequestParam String role,
            @RequestParam String email,
            @RequestParam String url,
            @RequestPart(value = "directorImage", required = false) MultipartFile directorImageFile
    ) throws JsonProcessingException {

        WebVisionMission vm = new ObjectMapper().readValue(vmJson, WebVisionMission.class);
        return ResponseEntity.ok(service.create(vm, role, email, directorImageFile, url));
    }

    @GetMapping("/getAllVisionMissions")
    public ResponseEntity<List<WebVisionMission>> getAllByBranchCode(@RequestParam String role,
                                                                     @RequestParam(required = false) String email,
                                                                     @RequestParam String branchCode,
                                                                     @RequestParam String url) {
        return ResponseEntity.ok(service.getAllByBranchCode(role, email, branchCode, url));
    }

    @GetMapping("/getVisionMissionById/{id}")
    public ResponseEntity<WebVisionMission> getVisionMissionById(@PathVariable Long id,
                                                                 @RequestParam String role,
                                                                 @RequestParam String email,
                                                                 @RequestParam String url) {
        return ResponseEntity.ok(service.getById(id, role, email, url));
    }

    @PutMapping(value = "/updateVisionMission/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebVisionMission> updateVisionMission(
            @PathVariable Long id,
            @RequestPart("vm") String vmJson,
            @RequestPart(value = "directorImage", required = false) MultipartFile directorImage,
            @RequestParam String role,
            @RequestParam String email,
            @RequestParam String url) throws JsonProcessingException {

        WebVisionMission vm = new ObjectMapper().readValue(vmJson, WebVisionMission.class);
        return ResponseEntity.ok(service.update(id, vm, role, email, directorImage, url));
    }

    @DeleteMapping("/deleteVisionMission/{id}")
    public ResponseEntity<String> deleteVisionMission(@PathVariable Long id,
                                                      @RequestParam String role,
                                                      @RequestParam String email,
                                                      @RequestParam String url) {
        service.delete(id, role, email, url);
        return ResponseEntity.ok("VisionMission deleted successfully");
    }
}