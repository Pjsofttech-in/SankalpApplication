package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.mapper.VMSubcategoryMapper;
import com.sankalpapp.dto.response.VMSubcategoryDTO;
import com.sankalpapp.entity.VMCategory;
import com.sankalpapp.entity.VMSubcategory;
import com.sankalpapp.repository.VMCategoryRepository;
import com.sankalpapp.repository.VMSubcategoryRepository;
import com.sankalpapp.service.VMSubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VMSubcategoryServiceImpl implements VMSubcategoryService {

    @Autowired
    private VMSubcategoryRepository vmSubcategoryRepository;

    @Autowired
    private VMSubcategoryMapper vmSubcategoryMapper;

    @Autowired
    private VMCategoryRepository vmCategoryRepository;

    @Override
    public VMSubcategoryDTO save(VMSubcategoryDTO vmSubcategoryDTO) {
        if (vmSubcategoryDTO.getCategoryId() == null) {
            throw new IllegalArgumentException("Category ID must be provided");
        }

        // Fetch category from the database using the categoryId in DTO
        VMCategory vmCategory = vmCategoryRepository.findById(vmSubcategoryDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category ID does not exist in the database"));

        // Convert DTO to entity
        VMSubcategory vmSubcategory = VMSubcategoryMapper.toEntity(vmSubcategoryDTO);
        vmSubcategory.setVmCategory(vmCategory); // Set category from repository
        vmSubcategory.setCreatedDate(LocalDate.now()); // Set created date

        // Save the subcategory entity
        VMSubcategory savedSubcategory = vmSubcategoryRepository.save(vmSubcategory);
        vmSubcategory.setCategoryName(vmCategory.getCategoryName());
        // Convert saved entity back to DTO using mapper
        return VMSubcategoryMapper.toDTO(savedSubcategory);
    }

    @Override
    public Optional<VMSubcategory> findById(Long id) {
        return vmSubcategoryRepository.findById(id);
    }

    @Override
    public List<VMSubcategory> findAll() {
        return vmSubcategoryRepository.findAll();
    }

    @Override
    public VMSubcategoryDTO updateSubCategory(Long id, VMSubcategoryDTO vmSubcategoryDTO) {
        VMSubcategory existingSubcategory = vmSubcategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subcategory not found with id: " + id));

        // Fetch category from the database using the categoryId in DTO
        VMCategory vmCategory = vmCategoryRepository.findById(vmSubcategoryDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category ID does not exist in the database"));

        existingSubcategory.setSubcategoryName(vmSubcategoryDTO.getSubcategoryName());
        existingSubcategory.setVmCategory(vmCategory);

        // Save the updated subcategory
        VMSubcategory updatedSubcategory = vmSubcategoryRepository.save(existingSubcategory);

        // Convert updated entity back to DTO
        VMSubcategoryDTO updatedDTO = new VMSubcategoryDTO();
        updatedDTO.setId(updatedSubcategory.getId());
        updatedDTO.setSubcategoryName(updatedSubcategory.getSubcategoryName());
        updatedDTO.setCategoryId(updatedSubcategory.getVmCategory().getId());

        return updatedDTO;
    }

    @Override
    public boolean deleteById(Long id) {
        if (vmSubcategoryRepository.existsById(id)) {
            vmSubcategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<VMSubcategory> findSubcategoryByName(String subcategoryName) {
        return vmSubcategoryRepository.findByName(subcategoryName);
    }

    @Override
    public List<VMSubcategory> getSubcategoriesByCategory(String categoryName) {
        return vmSubcategoryRepository.findByCategoryName(categoryName);
    }


    @Override
    public List<VMSubcategoryDTO> getSubcategoryByCategoryName(String categoryName) {
        List<VMSubcategory> vmSubcategories = vmSubcategoryRepository.findByVmCategoryCategoryName(categoryName);
        return vmSubcategories.stream()
                .map(vmSubcategory -> {
                    VMSubcategoryDTO dto = new VMSubcategoryDTO();
                    dto.setId(vmSubcategory.getId());
                    dto.setSubcategoryName(vmSubcategory.getSubcategoryName());
                    dto.setCategoryId(vmSubcategory.getVmCategory().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
