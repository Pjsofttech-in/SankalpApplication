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
public class VisionMissionController {

    @Autowired
    private VisionMissionService service;

    @PostMapping(value = "/createVisionMission", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebVisionMission> createVisionMission(
            @RequestPart("vm") String vmJson,
            @RequestParam(required = false) String url,
            @RequestPart(value = "directorImage", required = false) MultipartFile directorImageFile
    ) throws JsonProcessingException {

        WebVisionMission vm = new ObjectMapper().readValue(vmJson, WebVisionMission.class);
        return ResponseEntity.ok(service.create(vm, directorImageFile, url));
    }

    @GetMapping("/getAllVisionMissions")
    public ResponseEntity<List<WebVisionMission>> getAllByBranchCode(@RequestParam(required = false) String url) {
        return ResponseEntity.ok(service.getAllByBranchCode(url));
    }

    @GetMapping("/getVisionMissionById/{id}")
    public ResponseEntity<WebVisionMission> getVisionMissionById(@PathVariable Long id,
                                                                 @RequestParam(required = false) String url) {
        return ResponseEntity.ok(service.getById(id, url));
    }

    @PutMapping(value = "/updateVisionMission/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebVisionMission> updateVisionMission(
            @PathVariable Long id,
            @RequestPart("vm") String vmJson,
            @RequestPart(value = "directorImage", required = false) MultipartFile directorImage,
            @RequestParam(required = false) String url) throws JsonProcessingException {

        WebVisionMission vm = new ObjectMapper().readValue(vmJson, WebVisionMission.class);
        return ResponseEntity.ok(service.update(id, vm, directorImage, url));
    }

    @DeleteMapping("/deleteVisionMission/{id}")
    public ResponseEntity<String> deleteVisionMission(@PathVariable Long id,
                                                      @RequestParam(required = false) String url) {
        service.delete(id, url);
        return ResponseEntity.ok("VisionMission deleted successfully");
    }
}