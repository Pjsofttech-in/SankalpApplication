package com.sankalpapp.dto.mapper;

import com.sankalpapp.dto.response.VMSubcategoryDTO;
import com.sankalpapp.entity.VMSubcategory;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class VMSubcategoryMapper {

    // Map Subcategory entity to SubcategoryDTO
    public static VMSubcategoryDTO toDTO(VMSubcategory vmSubcategory) {
        if (vmSubcategory == null) {
            return null;
        }

        VMSubcategoryDTO vmSubcategoryDTO = new VMSubcategoryDTO();
        vmSubcategoryDTO.setId(vmSubcategory.getId());
        vmSubcategoryDTO.setSubcategoryName(vmSubcategory.getSubcategoryName());
        vmSubcategoryDTO.setCreatedDate(vmSubcategory.getCreatedDate());

        // Map category information
        if (vmSubcategory.getVmCategory() != null) {
            vmSubcategoryDTO.setCategoryId(vmSubcategory.getVmCategory().getId());
            vmSubcategoryDTO.setCategoryName(vmSubcategory.getVmCategory().getCategoryName());
        }

        // Map material IDs
        if (vmSubcategory.getVmMaterials() != null) {
            vmSubcategoryDTO.setMaterialIds(
                    vmSubcategory.getVmMaterials().stream()
                            .map(vmMaterial -> vmMaterial.getId())
                            .collect(Collectors.toList())
            );
        }

        return vmSubcategoryDTO;
    }

    // Map SubcategoryDTO to Subcategory entity
    public static VMSubcategory toEntity(VMSubcategoryDTO vmSubcategoryDTO) {
        if (vmSubcategoryDTO == null) {
            return null;
        }

        VMSubcategory vmSubcategory = new VMSubcategory();
        vmSubcategory.setId(vmSubcategoryDTO.getId());
        vmSubcategory.setSubcategoryName(vmSubcategoryDTO.getSubcategoryName());
        vmSubcategory.setCreatedDate(vmSubcategoryDTO.getCreatedDate());

        // Category and Material objects must be set from repositories in the service layer
        return vmSubcategory;
    }
}
