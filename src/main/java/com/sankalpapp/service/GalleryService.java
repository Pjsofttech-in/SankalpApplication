package com.sankalpapp.service;

import com.sankalpapp.dto.Request.GalleryRequest;
import com.sankalpapp.dto.Response.GalleryResponse;

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