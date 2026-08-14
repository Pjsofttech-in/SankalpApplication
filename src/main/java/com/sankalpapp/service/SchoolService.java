package com.sankalpapp.service;

import com.sankalpapp.dto.Response.SchoolResponse;
import com.sankalpapp.entity.School;

import java.util.List;

public interface SchoolService {

    SchoolResponse saveSchool(School school);

    SchoolResponse updateSchool(Long id, School school);

    void deleteSchool(Long id);

    SchoolResponse getSchoolById(Long id);

    List<SchoolResponse> getSchoolByCenterIdOrName(Long id, String centerName);

    List<SchoolResponse> getAllSchools();
}