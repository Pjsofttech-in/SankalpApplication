package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebAboutUs;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AboutUsService {
    public WebAboutUs createAboutUs(WebAboutUs webAboutUs, String role, String email, MultipartFile aboutUsImage, String url);
    List<WebAboutUs> getAllAboutUsByBranchCode(String role, String email, String url, String branchCode);
    WebAboutUs updateAboutUs(int id, WebAboutUs webAboutUs, String role, String email, MultipartFile aboutUsImage, String url);
    void deleteAboutUs(int id, String role, String email, String url);
    WebAboutUs getAboutUsById(int id, String role, String email, String url);
}
