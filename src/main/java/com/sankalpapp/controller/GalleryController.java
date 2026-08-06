package com.sankalpapp.controller;

import com.sankalpapp.dto.Request.GalleryRequest;
import com.sankalpapp.dto.Response.GalleryResponse;
import com.sankalpapp.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GalleryController {

    private final GalleryService galleryService;

    // Save Gallery
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public GalleryResponse saveGallery(@RequestBody GalleryRequest request) {
        return galleryService.saveGallery(request);
    }

    // Get All Gallery
    @GetMapping
    @PreAuthorize("permitAll()")
    public List<GalleryResponse> getAllGallery() {
        return galleryService.getAllGallery();
    }

    // Get Active Gallery
    @GetMapping("/active")
    @PreAuthorize("permitAll()")
    public List<GalleryResponse> getActiveGallery() {
        return galleryService.getActiveGallery();
    }

    // Get Gallery By Category
    @GetMapping("/category/{category}")
    @PreAuthorize("permitAll()")
    public List<GalleryResponse> getGalleryByCategory(@PathVariable String category) {
        return galleryService.getGalleryByCategory(category);
    }

    // Get Gallery By Id
    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public GalleryResponse getGalleryById(@PathVariable Long id) {
        return galleryService.getGalleryById(id);
    }

    // Update Gallery
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public GalleryResponse updateGallery(@PathVariable Long id,
                                         @RequestBody GalleryRequest request) {
        return galleryService.updateGallery(id, request);
    }

    // Delete Gallery
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteGallery(@PathVariable Long id) {

        galleryService.deleteGallery(id);

        return "Gallery Deleted Successfully";
    }
}