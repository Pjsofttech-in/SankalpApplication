package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebSlideBar;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SlideBarService {
    WebSlideBar createSlideBar(WebSlideBar webSlideBar, List<MultipartFile> slideBarImages, String url);

    List<WebSlideBar> getAllByBranchCode(String url);

    WebSlideBar updateSlideBar(Long id, WebSlideBar webSlideBar,
                               List<MultipartFile> newImages, List<String> deleteImages, String url);

    void deleteSlideBar(Long id, String url);

    WebSlideBar getSlideBarById(Long id, String url);
}