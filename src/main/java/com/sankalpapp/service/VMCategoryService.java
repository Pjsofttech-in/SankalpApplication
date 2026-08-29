package com.sankalpapp.service;

import com.sankalpapp.dto.response.VMCategoryDTO;
import com.sankalpapp.entity.VMCategory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface VMCategoryService {

    VMCategory saveCategory(VMCategory vmCategory, MultipartFile thumbnailFile, Long materialtype_id);

    VMCategory getCategoryById(Long id);

    List<VMCategoryDTO> getAllCategories();

    VMCategory updateCategory(Long id, VMCategory vmCategory, MultipartFile thumbnail) throws IOException;

    void deleteCategory(Long id);

    Optional<VMCategory> findCategoryByName(String categoryName);

    List<VMCategoryDTO> getCategoriesByMaterialTypeName(String materialTypeName);

    List<VMCategory> getCategoriesByDateRange(String filter, String startDateStr, String endDateStr);

}
