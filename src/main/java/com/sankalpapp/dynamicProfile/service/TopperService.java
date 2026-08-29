package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebTopper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TopperService {
    WebTopper createTopper(WebTopper webTopper, MultipartFile topperImage, String url);

    List<WebTopper> getAllToppersByBranchCode(String url);

    WebTopper updateTopper(Long id, WebTopper updatedWebTopper, MultipartFile topperImage, String url);

    void deleteTopper(Long id, String url);

    WebTopper getTopperById(Long id, String url);
}
