package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebFaculty;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FacultyService {
    WebFaculty createFacility(WebFaculty webFaculty, MultipartFile image, String url);
    List<WebFaculty> getAllFacilitiesByBranchCode(String url);
    WebFaculty updateFacility(Long id, WebFaculty webFaculty, MultipartFile image, String url);
    void deleteFacility(Long id, String url);
    WebFaculty getFacilityById(Long id, String url);
}

