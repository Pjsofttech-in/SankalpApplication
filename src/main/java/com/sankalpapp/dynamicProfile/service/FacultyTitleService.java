package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebFacultyTitle;

import java.util.List;

public interface FacultyTitleService {
    WebFacultyTitle createFacilityTitle(WebFacultyTitle webFacultyTitle, String url);

    List<WebFacultyTitle> getAllFacilityTitlesByBranchCode(String url);

    WebFacultyTitle updateFacilityTitle(Long id, WebFacultyTitle webFacultyTitle, String url);

    void deleteFacilityTitle(Long id, String url);

    WebFacultyTitle getFacilityTitleById(Long id, String url);
}