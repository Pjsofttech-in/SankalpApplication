package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebMapAndImages;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MapAndImagesService {
    WebMapAndImages create(WebMapAndImages entity, MultipartFile imageFile, String url);

    List<WebMapAndImages> getAllByBranchCode(String url);

    WebMapAndImages update(Long id, WebMapAndImages webMapAndImages, MultipartFile imageFile, String url);

    void delete(Long id, String url);

    WebMapAndImages getById(Long id, String url);
}