package com.testapplication.service;

import com.testapplication.entity.District;

import java.util.List;

public interface DistrictService {

    District saveDistrict(District district);

    District updateDistrict(Long id, District district);

    void deleteDistrict(Long id);

    District getDistrictById(Long id);

    List<District> getAllDistricts();
}