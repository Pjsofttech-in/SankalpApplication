package com.testapplication.service;

import com.testapplication.entity.Gallery;

import java.util.List;

public interface GalleryService {

    Gallery saveGallery(Gallery gallery);

    Gallery updateGallery(Long id, Gallery gallery);

    void deleteGallery(Long id);

    Gallery getGalleryById(Long id);

    List<Gallery> getAllGallery();
}