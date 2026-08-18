package com.sankalpapp.service;

import com.sankalpapp.dto.Request.TalukaRequest;
import com.sankalpapp.dto.Response.TalukaDTO;

import java.util.List;

public interface TalukaService {

    TalukaDTO saveTaluka(TalukaRequest taluka);

    TalukaDTO updateTaluka(Long id, TalukaRequest request);

    void deleteTaluka(Long id);

    TalukaDTO getTalukaById(Long id);

    List<TalukaDTO> getAllTalukas();

    List<TalukaDTO> getTalukasByDistrict(Long districtId);

}