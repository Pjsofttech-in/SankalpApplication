package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebManuBar;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ManuBarService {
     WebManuBar createManuBar(WebManuBar webManuBar, String role, String email, MultipartFile menubarImage, String url);
    List<WebManuBar> getAllByBranchCode(String role, String email, String url, String branchCode);
    WebManuBar updateManuBar(Long id, WebManuBar webManuBar, String role, String email, MultipartFile menubarImage, String url);
    void deleteManuBar(Long id, String role, String email, String url);
    WebManuBar getManuBarById(Long id, String role, String email, String url);
}