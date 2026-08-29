package com.sankalpapp.serviceimpl;

import com.sankalpapp.entity.VMMaterialType;
import com.sankalpapp.repository.VMMaterialTypeRepository;
import com.sankalpapp.service.VMMaterialTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VMMaterialTypeServiceImpl implements VMMaterialTypeService {

    @Autowired
    private VMMaterialTypeRepository vmMaterialTypeRepository;

    @Override
    public VMMaterialType createMaterialType(VMMaterialType vmMaterialType) {
        return vmMaterialTypeRepository.save(vmMaterialType);
    }

    @Override
    public Optional<VMMaterialType> getMaterialTypeById(Long id) {
        return vmMaterialTypeRepository.findById(id);
    }

    @Override
    public List<VMMaterialType> getAllMaterialTypes() {
        return vmMaterialTypeRepository.findAll();
    }

    @Override
    public VMMaterialType updateMaterialType(Long id, VMMaterialType vmMaterialType) {
        Optional<VMMaterialType> existingMaterial = vmMaterialTypeRepository.findById(id);
        if (existingMaterial.isPresent()) {
            VMMaterialType updatedMaterial = existingMaterial.get();
            updatedMaterial.setMaterialtype(vmMaterialType.getMaterialtype());
            return vmMaterialTypeRepository.save(updatedMaterial);
        }
        return null;
    }

    @Override
    public void deleteMaterialType(Long id) {
        vmMaterialTypeRepository.deleteById(id);
    }
}

