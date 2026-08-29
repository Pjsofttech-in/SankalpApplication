package com.sankalpapp.service;

import com.sankalpapp.dto.response.VMSubcategoryDTO;
import com.sankalpapp.entity.VMSubcategory;

import java.util.List;
import java.util.Optional;

public interface VMSubcategoryService {
    VMSubcategoryDTO save(VMSubcategoryDTO vmSubcategoryDTO);

    Optional<VMSubcategory> findById(Long id);

    List<VMSubcategory> findAll();

    VMSubcategoryDTO updateSubCategory(Long id, VMSubcategoryDTO vmSubcategoryDTO);

    boolean deleteById(Long id);

    Optional<VMSubcategory> findSubcategoryByName(String subcategoryName);

    List<VMSubcategory> getSubcategoriesByCategory(String categoryName);

    List<VMSubcategoryDTO> getSubcategoryByCategoryName(String categoryName);

}
