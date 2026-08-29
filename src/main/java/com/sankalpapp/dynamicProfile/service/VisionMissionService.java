package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebVisionMission;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VisionMissionService {
    WebVisionMission create(WebVisionMission vm, MultipartFile directorImage, String url);

    List<WebVisionMission> getAllByBranchCode(String url);

    WebVisionMission update(Long id, WebVisionMission vm, MultipartFile directorImage, String url);

    void delete(Long id, String url);

    WebVisionMission getById(Long id, String url);
}