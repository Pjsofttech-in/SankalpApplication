package com.sankalpapp.dto.mapper;

import com.sankalpapp.dto.response.VMMaterialDTO;
import com.sankalpapp.entity.VMMaterial;
import com.sankalpapp.entity.VMSubcategory;
import org.springframework.stereotype.Component;

@Component
public class VMMaterialMapper {

    public static VMMaterialDTO toDTO(VMMaterial vmMaterial) {
        VMMaterialDTO dto = new VMMaterialDTO();
        dto.setId(vmMaterial.getId());
        dto.setMaterialtype(vmMaterial.getMaterialtype());
        // dto.setMaterialName(material.getMaterialName());
        dto.setPdfFile(vmMaterial.getPdfFile());
        dto.setThumbnailFile(vmMaterial.getThumbnailFile());
        dto.setSaveToDevice(vmMaterial.getSaveToDevice());
        dto.setDemoPdf(vmMaterial.getDemoPdf());
        dto.setStatus(vmMaterial.getStatus());
        dto.setMrp(vmMaterial.getMrp());
        dto.setPrice(vmMaterial.getPrice());
        dto.setValidity(vmMaterial.getValidity());
        dto.setCreatedDate(vmMaterial.getCreatedDate());
        dto.setSubcategoryId(vmMaterial.getVmSubcategory().getId());
        dto.setSubcategoryName(vmMaterial.getVmSubcategory().getSubcategoryName());
        dto.setCategoryName(vmMaterial.getVmSubcategory().getVmCategory().getCategoryName());
        return dto;
    }

    public static VMMaterial toEntity(VMMaterialDTO dto, VMSubcategory vmSubcategory) {
        VMMaterial vmMaterial = new VMMaterial();
        vmMaterial.setMaterialtype(dto.getMaterialtype());
        //material.setMaterialName(dto.getMaterialName());
        vmMaterial.setPdfFile(dto.getPdfFile());
        vmMaterial.setThumbnailFile(dto.getThumbnailFile());
        vmMaterial.setSaveToDevice(dto.getSaveToDevice());
        vmMaterial.setDemoPdf(dto.getDemoPdf());
        vmMaterial.setStatus(dto.getStatus());
        vmMaterial.setMrp(dto.getMrp());
        vmMaterial.setPrice(dto.getPrice());
        vmMaterial.setValidity(dto.getValidity());
        vmMaterial.setVmSubcategory(vmSubcategory);
        return vmMaterial;
    }
}
