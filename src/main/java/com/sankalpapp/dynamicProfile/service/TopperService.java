package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebTopper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TopperService {
    WebTopper createTopper(WebTopper webTopper, String role, String email, MultipartFile topperImage, String url);
    List<WebTopper> getAllToppersByBranchCode(String role, String email, String branchCode, String url);
    WebTopper updateTopper(Long id, WebTopper updatedWebTopper, String role, String email, MultipartFile topperImage, String url);
    void deleteTopper(Long id, String role, String email, String url);
    WebTopper getTopperById(Long id, String role, String email, String url);
}
