package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.dto.WebJobCareerOptionDTO;
import com.sankalpapp.dynamicProfile.entity.WebJobCareerOption;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface JobCareerOptionService {
    WebJobCareerOptionDTO create(WebJobCareerOption option, String role, String email, MultipartFile resumeFile, String url, Long webHRDetailsId);
    WebJobCareerOptionDTO update(Long id, WebJobCareerOption option, String role, String email, MultipartFile resumeFile, String url, Long webHRDetailsId);
    List<WebJobCareerOptionDTO> getAllByBranchCode(String role, String email, String url, String branchCode);
    WebJobCareerOptionDTO getById(Long id, String role, String email, String url);
    void delete(Long id, String role, String email, String url);
}