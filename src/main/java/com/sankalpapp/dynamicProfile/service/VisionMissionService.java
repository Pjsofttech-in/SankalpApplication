package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebVisionMission;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VisionMissionService {
    WebVisionMission create(WebVisionMission vm, String role, String email, MultipartFile directorImage, String url);
    List<WebVisionMission> getAllByBranchCode(String role, String email, String branchCode, String url);
    WebVisionMission update(Long id, WebVisionMission vm, String role, String email, MultipartFile directorImage, String url);
    void delete(Long id, String role, String email, String url);
    WebVisionMission getById(Long id, String role, String email, String url);
}