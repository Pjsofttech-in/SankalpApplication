package com.sankalpapp.service;

import com.sankalpapp.dto.response.VMUserMaterialResponseDTO;
import com.sankalpapp.entity.VMUserMaterialAssociation;

import java.util.List;

public interface VMUserMaterialService {
    VMUserMaterialAssociation addUserMaterialAssociation(String email, Long materialId, Double paidAmount);

    VMUserMaterialResponseDTO getUserAndMaterialsByUsernameAndEmail(String email);

    List<VMUserMaterialAssociation> getAllUserMaterialAssociations();
}
