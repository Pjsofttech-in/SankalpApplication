package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebAboutUs;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AboutUsService {
    WebAboutUs createAboutUs(WebAboutUs webAboutUs, MultipartFile aboutUsImage, String url);

    List<WebAboutUs> getAllAboutUsByBranchCode(String url);

    WebAboutUs updateAboutUs(int id, WebAboutUs webAboutUs, MultipartFile aboutUsImage, String url);

    void deleteAboutUs(int id, String url);

    WebAboutUs getAboutUsById(int id, String url);
}
