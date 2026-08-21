package com.sankalpapp.dynamicProfile.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankalpapp.dynamicProfile.entity.WebGallery;
import com.sankalpapp.dynamicProfile.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class GalleryController {

    @Autowired
    private GalleryService service;

    @PostMapping(value = "/createGallery", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebGallery> createGallery(@RequestParam("gallery") String galleryJson,
                                                    @RequestParam String role,
                                                    @RequestParam String email,
                                                    @RequestParam String url,
                                                    @RequestParam(value = "images", required = false) List<MultipartFile> images)
            throws Exception {
        WebGallery webGallery = new ObjectMapper().readValue(galleryJson, WebGallery.class);
        return ResponseEntity.ok(service.createGallery(webGallery, role, email, images, url));
    }

    @GetMapping("/getAllGalleries")
    public ResponseEntity<List<WebGallery>> getAllGalleriesByBranchCode(@RequestParam String role,
                                                                        @RequestParam(required = false) String email,
                                                                        @RequestParam String url,
                                                                        @RequestParam String branchCode) {
        return ResponseEntity.ok(service.getAllGalleriesByBranchCode(role, email, url, branchCode));
    }

    @GetMapping("/getGalleryById/{id}")
    public ResponseEntity<WebGallery> getGalleryById(@PathVariable Long id,
                                                     @RequestParam String role,
                                                     @RequestParam String email,
                                                     @RequestParam String url) {
        return ResponseEntity.ok(service.getGalleryById(id, role, email, url));
    }

    @PutMapping(value = "/updateGallery/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebGallery> updateGallery(@PathVariable Long id,
                                                    @RequestParam(value = "gallery", required = false) String galleryJson,
                                                    @RequestParam String role,
                                                    @RequestParam String email,
                                                    @RequestParam String url,
                                                    @RequestParam(value = "newImages", required = false) List<MultipartFile> newImages,
                                                    @RequestParam(value = "deleteImages", required = false) List<MultipartFile> deleteImageFiles)
            throws Exception {

        WebGallery webGallery = (galleryJson != null) ? new ObjectMapper().readValue(galleryJson, WebGallery.class) : new WebGallery();
        List<String> deleteImageNames = (deleteImageFiles != null)
                ? deleteImageFiles.stream().map(MultipartFile::getOriginalFilename).toList()
                : null;

        return ResponseEntity.ok(service.updateGallery(id, webGallery, role, email, newImages, deleteImageNames, url));
    }

    @DeleteMapping("/deleteGallery/{id}")
    public ResponseEntity<String> deleteGallery(@PathVariable Long id,
                                                @RequestParam String role,
                                                @RequestParam String email,
                                                @RequestParam String url) {
        service.deleteGallery(id, role, email, url);
        return ResponseEntity.ok("Gallery deleted successfully");
    }
}
