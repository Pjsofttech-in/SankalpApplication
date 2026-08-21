package com.sankalpapp.dynamicProfile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankalpapp.dynamicProfile.entity.WebMapAndImages;
import com.sankalpapp.dynamicProfile.service.MapAndImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class MapAndImagesController {

    @Autowired
    private MapAndImagesService service;

    @PostMapping(value = "/createMapAndImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebMapAndImages> create(
            @RequestPart("mapAndImages") String mapAndImagesJson,
            @RequestParam String role,
            @RequestParam String email,
            @RequestParam String url,
            @RequestParam("contactImage") MultipartFile imageFile) throws JsonProcessingException {

        WebMapAndImages webMapAndImages = new ObjectMapper().readValue(mapAndImagesJson, WebMapAndImages.class);
        return ResponseEntity.ok(service.create(webMapAndImages, role, email, imageFile, url));
    }

    @GetMapping("/getAllMapAndImages")
    public ResponseEntity<List<WebMapAndImages>> getAllByBranchCode(@RequestParam String role,
                                                                    @RequestParam(required = false) String email,
                                                                    @RequestParam String url,
                                                                    @RequestParam String branchCode) {
        return ResponseEntity.ok(service.getAllByBranchCode(role, email, url, branchCode));
    }

    @GetMapping("/getMapAndImagesById/{id}")
    public ResponseEntity<WebMapAndImages> getById(@PathVariable Long id,
                                                   @RequestParam String role,
                                                   @RequestParam String email,
                                                   @RequestParam String url) {
        return ResponseEntity.ok(service.getById(id, role, email, url));
    }

    @PutMapping(value = "/updateMapAndImages/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebMapAndImages> update(
            @PathVariable Long id,
            @RequestPart("mapAndImages") String mapAndImagesJson,
            @RequestPart(value = "contactImage", required = false) MultipartFile imageFile,
            @RequestParam String role,
            @RequestParam String email,
            @RequestParam String url) throws JsonProcessingException {

        WebMapAndImages updated = new ObjectMapper().readValue(mapAndImagesJson, WebMapAndImages.class);
        return ResponseEntity.ok(service.update(id, updated, role, email, imageFile, url));
    }

    @DeleteMapping("/deleteMapAndImages/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id,
                                         @RequestParam String role,
                                         @RequestParam String email,
                                         @RequestParam String url) {
        service.delete(id, role, email, url);
        return ResponseEntity.ok("MapAndImages deleted successfully");
    }
}
