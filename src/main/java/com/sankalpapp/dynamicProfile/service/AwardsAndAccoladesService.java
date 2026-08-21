package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebAwardsAndAccolades;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AwardsAndAccoladesService {
    WebAwardsAndAccolades createAward(WebAwardsAndAccolades award, MultipartFile awardImage, String url);
    List<WebAwardsAndAccolades> getAllAwardsByBranchCode(String url);
    WebAwardsAndAccolades updateAward(Long id, WebAwardsAndAccolades award, MultipartFile awardImage, String url);
    void deleteAward(Long id, String url);
    WebAwardsAndAccolades getAwardById(Long id, String url);
}
