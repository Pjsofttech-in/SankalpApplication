package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebMapAndImages;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MapAndImagesService {
    WebMapAndImages create(WebMapAndImages entity, String role, String email, MultipartFile imageFile, String url);
    List<WebMapAndImages> getAllByBranchCode(String role, String email, String url, String branchCode);
    WebMapAndImages update(Long id, WebMapAndImages webMapAndImages, String role, String email, MultipartFile imageFile, String url);
    void delete(Long id, String role, String email, String url);
    WebMapAndImages getById(Long id, String role, String email, String url);
}