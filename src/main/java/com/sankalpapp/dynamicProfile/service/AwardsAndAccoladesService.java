package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebAwardsAndAccolades;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AwardsAndAccoladesService {
    WebAwardsAndAccolades createAward(WebAwardsAndAccolades award, String role, String email, MultipartFile awardImage, String url);
    List<WebAwardsAndAccolades> getAllAwardsByBranchCode(String role, String email, String url, String branchCode);
    WebAwardsAndAccolades updateAward(Long id, WebAwardsAndAccolades award, String role, String email, MultipartFile awardImage, String url);
    void deleteAward(Long id, String role, String email, String url);
    WebAwardsAndAccolades getAwardById(Long id, String role, String email, String url);
}
