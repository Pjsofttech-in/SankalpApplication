package com.testapplication.service;

import com.testapplication.dto.Request.GalleryRequest;
import com.testapplication.dto.Response.GalleryResponse;

import java.util.List;

public interface GalleryService {

    GalleryResponse saveGallery(GalleryRequest request);

    GalleryResponse updateGallery(Long id, GalleryRequest request);

    void deleteGallery(Long id);

    GalleryResponse getGalleryById(Long id);

    List<GalleryResponse> getAllGallery();

    List<GalleryResponse> getActiveGallery();

    List<GalleryResponse> getGalleryByCategory(String category);
}