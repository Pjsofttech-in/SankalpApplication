package com.sankalpapp.dynamicProfile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankalpapp.dynamicProfile.entity.WebManuBar;
import com.sankalpapp.dynamicProfile.service.ManuBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ManuBarController {

    @Autowired
    private ManuBarService service;

    @PostMapping(value = "/createManuBar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebManuBar> createManuBar(
            @RequestPart("manuBar") String manuBarJson,
            @RequestParam String url,
            @RequestParam("menubarImageName") MultipartFile imageFile) throws JsonProcessingException {


        WebManuBar webManuBar = new ObjectMapper().readValue(manuBarJson, WebManuBar.class);

        return ResponseEntity.ok(service.createManuBar(webManuBar, imageFile, url));
    }

    @GetMapping("/getAllManuBars")
    public ResponseEntity<List<WebManuBar>> getAllByBranchCode(@RequestParam String url) {
        return ResponseEntity.ok(service.getAllByBranchCode(url));
    }

    @GetMapping("/getManuBarById/{id}")
    public ResponseEntity<WebManuBar> getManuBarById(@PathVariable Long id,
                                                     @RequestParam String url) {
        return ResponseEntity.ok(service.getManuBarById(id, url));
    }

    @PutMapping(value = "/updateManuBar/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebManuBar> updateManuBar(@PathVariable Long id,
                                                    @RequestPart("manuBar") String manuBarJson,
                                                    @RequestPart(value = "menubarImage", required = false) MultipartFile menubarImage,
                                                    @RequestParam String url) throws JsonProcessingException {

        WebManuBar webManuBar = new ObjectMapper().readValue(manuBarJson, WebManuBar.class);
        return ResponseEntity.ok(service.updateManuBar(id, webManuBar, menubarImage, url));
    }

    @DeleteMapping("/deleteManuBar/{id}")
    public ResponseEntity<String> deleteManuBar(@PathVariable Long id,
                                                @RequestParam String url) {
        service.deleteManuBar(id, url);
        return ResponseEntity.ok("ManuBar deleted successfully");
    }
}
