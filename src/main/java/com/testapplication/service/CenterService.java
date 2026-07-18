package com.testapplication.service;

import com.testapplication.dto.Request.CenterRequest;
import com.testapplication.dto.Response.CenterResponse;

import java.util.List;

public interface CenterService {

    CenterResponse saveCenter(CenterRequest request);

    CenterResponse updateCenter(Long id, CenterRequest request);

    void deleteCenter(Long id);

    CenterResponse getCenterById(Long id);

    List<CenterResponse> getAllCenters();

    List<CenterResponse> getCentersByTaluka(Long talukaId);
}