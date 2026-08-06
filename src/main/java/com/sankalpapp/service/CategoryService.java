package com.sankalpapp.service;

import com.sankalpapp.dto.Request.CategoryRequest;
import com.sankalpapp.dto.Response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse saveCategory(CategoryRequest request);

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);

    CategoryResponse getCategoryById(Long id);

    List<CategoryResponse> getAllCategories();
}