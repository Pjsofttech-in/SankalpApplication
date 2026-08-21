package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebGallery;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GalleryService {
    WebGallery createGallery(WebGallery webGallery, String role, String email, List<MultipartFile> images, String url);
    List<WebGallery> getAllGalleriesByBranchCode(String role, String email, String url, String branchCode);
    WebGallery updateGallery(Long id, WebGallery webGallery, String role, String email,
                             List<MultipartFile> newImages, List<String> deleteImages, String url);
    void deleteGallery(Long id, String role, String email, String url);
    WebGallery getGalleryById(Long id, String role, String email, String url);
}