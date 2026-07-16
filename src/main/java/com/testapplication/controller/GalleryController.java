package com.testapplication.controller;

import com.testapplication.entity.Gallery;
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
    public Gallery saveGallery(@RequestBody Gallery gallery) {
        return galleryService.saveGallery(gallery);
    }

    // All Gallery
    @GetMapping
    public List<Gallery> getAllGallery() {
        return galleryService.getAllGallery();
    }

    // Active Gallery (Frontend)
    @GetMapping("/active")
    public List<Gallery> getActiveGallery() {
        return galleryService.getActiveGallery();
    }

    // Category Wise Gallery
    @GetMapping("/category/{category}")
    public List<Gallery> getGalleryByCategory(@PathVariable String category) {
        return galleryService.getGalleryByCategory(category);
    }

    // By Id
    @GetMapping("/{id}")
    public Gallery getGalleryById(@PathVariable Long id) {
        return galleryService.getGalleryById(id);
    }

    // Update
    @PutMapping("/{id}")
    public Gallery updateGallery(@PathVariable Long id,
                                 @RequestBody Gallery gallery) {
        return galleryService.updateGallery(id, gallery);
    }

    // Delete
    @DeleteMapping("/{id}")
    public String deleteGallery(@PathVariable Long id) {

        galleryService.deleteGallery(id);

        return "Gallery Deleted Successfully";
    }
}