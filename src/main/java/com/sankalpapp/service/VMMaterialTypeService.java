package com.sankalpapp.service;

import com.sankalpapp.entity.VMMaterialType;

import java.util.List;
import java.util.Optional;

public interface VMMaterialTypeService {
    VMMaterialType createMaterialType(VMMaterialType vmMaterialType);

    Optional<VMMaterialType> getMaterialTypeById(Long id);

    List<VMMaterialType> getAllMaterialTypes();

    VMMaterialType updateMaterialType(Long id, VMMaterialType vmMaterialType);

    void deleteMaterialType(Long id);
}

