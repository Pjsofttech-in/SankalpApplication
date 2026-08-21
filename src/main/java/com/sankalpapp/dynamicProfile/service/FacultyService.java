package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebFaculty;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FacultyService {
    WebFaculty createFacility(WebFaculty webFaculty, String role, String email, MultipartFile image, String url);
    List<WebFaculty> getAllFacilitiesByBranchCode(String role, String email, String url, String branchCode);
    WebFaculty updateFacility(Long id, WebFaculty webFaculty, String role, String email, MultipartFile image, String url);
    void deleteFacility(Long id, String role, String email, String url);
    WebFaculty getFacilityById(Long id, String role, String email, String url);
}

