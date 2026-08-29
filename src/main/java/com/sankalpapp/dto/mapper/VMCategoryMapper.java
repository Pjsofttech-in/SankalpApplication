package com.sankalpapp.dto.mapper;

import com.sankalpapp.dto.response.VMCategoryDTO;
import com.sankalpapp.entity.VMCategory;
import org.springframework.stereotype.Component;

@Component
public class VMCategoryMapper {

    public static VMCategoryDTO toDTO(VMCategory vmCategory) {
        VMCategoryDTO dto = new VMCategoryDTO();
        dto.setId(vmCategory.getId());
        dto.setCategoryName(vmCategory.getCategoryName());
        //dto.setType(category.getType());
        dto.setThumbnail(vmCategory.getThumbnail());
        dto.setCreatedDate(vmCategory.getCreatedDate());
        return dto;
    }

    public static VMCategory toEntity(VMCategoryDTO vmCategoryDTO) {
        VMCategory vmCategory = new VMCategory();
        vmCategory.setCategoryName(vmCategoryDTO.getCategoryName());
        // category.setType(categoryDTO.getType());
        vmCategory.setThumbnail(vmCategoryDTO.getThumbnail());
        return vmCategory;
    }
}
