package com.testapplication.service;

import com.testapplication.entity.Center;

import java.util.List;

public interface CenterService {

    Center saveCenter(Center center);

    Center updateCenter(Long id, Center center);

    void deleteCenter(Long id);

    Center getCenterById(Long id);

    List<Center> getAllCenters();

    // Dynamic Dropdown
    List<Center> getCentersByTaluka(Long talukaId);

}