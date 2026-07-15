package com.testapplication.service;

import com.testapplication.entity.School;

import java.util.List;

public interface SchoolService {

    School saveSchool(School school);

    School updateSchool(Long id, School school);

    void deleteSchool(Long id);

    School getSchoolById(Long id);

    List<School> getAllSchools();
}