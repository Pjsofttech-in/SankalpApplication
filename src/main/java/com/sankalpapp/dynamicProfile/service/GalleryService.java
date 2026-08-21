package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebGallery;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GalleryService {
    WebGallery createGallery(WebGallery webGallery, List<MultipartFile> images, String url);
    List<WebGallery> getAllGalleriesByBranchCode(String url);
    WebGallery updateGallery(Long id, WebGallery webGallery,
                             List<MultipartFile> newImages, List<String> deleteImages, String url);
    void deleteGallery(Long id, String url);
    WebGallery getGalleryById(Long id, String url);
}