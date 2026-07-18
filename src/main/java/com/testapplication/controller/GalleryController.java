package com.testapplication.controller;

import com.testapplication.dto.Request.GalleryRequest;
import com.testapplication.dto.Response.GalleryResponse;
import com.testapplication.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GalleryController {

    private final GalleryService galleryService;

    // Save
    @PostMapping
    public GalleryResponse saveGallery(@RequestBody GalleryRequest request) {
        return galleryService.saveGallery(request);
    }

    // Get All
    @GetMapping
    public List<GalleryResponse> getAllGallery() {
        return galleryService.getAllGallery();
    }

    // Active Gallery
    @GetMapping("/active")
    public List<GalleryResponse> getActiveGallery() {
        return galleryService.getActiveGallery();
    }

    // Category Wise
    @GetMapping("/category/{category}")
    public List<GalleryResponse> getGalleryByCategory(@PathVariable String category) {
        return galleryService.getGalleryByCategory(category);
    }

    // Get By Id
    @GetMapping("/{id}")
    public GalleryResponse getGalleryById(@PathVariable Long id) {
        return galleryService.getGalleryById(id);
    }

    // Update
    @PutMapping("/{id}")
    public GalleryResponse updateGallery(@PathVariable Long id,
                                         @RequestBody GalleryRequest request) {
        return galleryService.updateGallery(id, request);
    }

    // Delete
    @DeleteMapping("/{id}")
    public String deleteGallery(@PathVariable Long id) {

        galleryService.deleteGallery(id);

        return "Gallery Deleted Successfully";
    }
}