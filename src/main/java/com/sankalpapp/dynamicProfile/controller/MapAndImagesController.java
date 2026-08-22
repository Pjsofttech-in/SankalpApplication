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
public class MapAndImagesController {

    @Autowired
    private MapAndImagesService service;

    @PostMapping(value = "/createMapAndImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebMapAndImages> create(
            @RequestPart("mapAndImages") String mapAndImagesJson,
            @RequestParam(required = false) String url,
            @RequestParam("contactImage") MultipartFile imageFile) throws JsonProcessingException {

        WebMapAndImages webMapAndImages = new ObjectMapper().readValue(mapAndImagesJson, WebMapAndImages.class);
        return ResponseEntity.ok(service.create(webMapAndImages, imageFile, url));
    }

    @GetMapping("/getAllMapAndImages")
    public ResponseEntity<List<WebMapAndImages>> getAllByBranchCode(@RequestParam(required = false) String url) {
        return ResponseEntity.ok(service.getAllByBranchCode(url));
    }

    @GetMapping("/getMapAndImagesById/{id}")
    public ResponseEntity<WebMapAndImages> getById(@PathVariable Long id,
                                                   @RequestParam(required = false) String url) {
        return ResponseEntity.ok(service.getById(id, url));
    }

    @PutMapping(value = "/updateMapAndImages/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebMapAndImages> update(
            @PathVariable Long id,
            @RequestPart("mapAndImages") String mapAndImagesJson,
            @RequestPart(value = "contactImage", required = false) MultipartFile imageFile,
            @RequestParam(required = false) String url) throws JsonProcessingException {

        WebMapAndImages updated = new ObjectMapper().readValue(mapAndImagesJson, WebMapAndImages.class);
        return ResponseEntity.ok(service.update(id, updated, imageFile, url));
    }

    @DeleteMapping("/deleteMapAndImages/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id,
                                         @RequestParam(required = false) String url) {
        service.delete(id, url);
        return ResponseEntity.ok("MapAndImages deleted successfully");
    }
}
