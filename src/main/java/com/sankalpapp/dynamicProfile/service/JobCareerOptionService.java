package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.dto.WebJobCareerOptionDTO;
import com.sankalpapp.dynamicProfile.entity.WebJobCareerOption;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface JobCareerOptionService {
    WebJobCareerOptionDTO create(WebJobCareerOption option, MultipartFile resumeFile, String url, Long webHRDetailsId);
    WebJobCareerOptionDTO update(Long id, WebJobCareerOption option, MultipartFile resumeFile, String url, Long webHRDetailsId);
    List<WebJobCareerOptionDTO> getAllByBranchCode(String url);
    WebJobCareerOptionDTO getById(Long id, String url);
    void delete(Long id, String url);
}