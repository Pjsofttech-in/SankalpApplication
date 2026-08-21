package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebFacultyTitle;

import java.util.List;

public interface FacultyTitleService {
    WebFacultyTitle createFacilityTitle(WebFacultyTitle webFacultyTitle, String role, String email, String url);
    List<WebFacultyTitle> getAllFacilityTitlesByBranchCode(String role, String email, String url, String branchCode);
    WebFacultyTitle updateFacilityTitle(Long id, WebFacultyTitle webFacultyTitle, String role, String email, String url);
    void deleteFacilityTitle(Long id, String role, String email, String url);
    WebFacultyTitle getFacilityTitleById(Long id, String role, String email, String url);
}