package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebSlideBar;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SlideBarService {
    WebSlideBar createSlideBar(WebSlideBar webSlideBar, String role, String email, List<MultipartFile> slideBarImages, String url);
    List<WebSlideBar> getAllByBranchCode(String role, String email, String branchCode, String url);
    WebSlideBar updateSlideBar(Long id, WebSlideBar webSlideBar, String role, String email,
                               List<MultipartFile> newImages, List<String> deleteImages, String url);

    void deleteSlideBar(Long id, String role, String email, String url);
    WebSlideBar getSlideBarById(Long id, String role, String email, String url);
}