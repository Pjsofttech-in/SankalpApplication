package com.sankalpapp.service;

import com.sankalpapp.entity.Taluka;

import java.util.List;

public interface TalukaService {

    Taluka saveTaluka(Taluka taluka);

    Taluka updateTaluka(Long id, Taluka taluka);

    void deleteTaluka(Long id);

    Taluka getTalukaById(Long id);

    List<Taluka> getAllTalukas();

    List<Taluka> getTalukasByDistrict(Long districtId);

}