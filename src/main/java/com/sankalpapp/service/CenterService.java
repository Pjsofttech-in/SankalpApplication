package com.sankalpapp.service;

import com.sankalpapp.dto.Request.CenterRequest;
import com.sankalpapp.dto.Response.CenterDTO;

import java.util.List;

public interface CenterService {

    CenterDTO saveCenter(CenterRequest request);

    CenterDTO updateCenter(Long id, CenterRequest request);

    void deleteCenter(Long id);

    CenterDTO getCenterById(Long id);

    List<CenterDTO> getAllCenters();

    List<CenterDTO> getCentersByTaluka(Long talukaId);

    List<String> getSchoolsByCenter(Long centerId);
}