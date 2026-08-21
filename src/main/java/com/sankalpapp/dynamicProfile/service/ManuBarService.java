package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebManuBar;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ManuBarService {
     WebManuBar createManuBar(WebManuBar webManuBar, MultipartFile menubarImage, String url);
    List<WebManuBar> getAllByBranchCode(String url);
    WebManuBar updateManuBar(Long id, WebManuBar webManuBar, MultipartFile menubarImage, String url);
    void deleteManuBar(Long id, String url);
    WebManuBar getManuBarById(Long id, String url);
}