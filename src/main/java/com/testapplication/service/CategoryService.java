package com.testapplication.service;

import com.testapplication.dto.Request.CategoryRequest;
import com.testapplication.dto.Response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse saveCategory(CategoryRequest request);

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);

    CategoryResponse getCategoryById(Long id);

    List<CategoryResponse> getAllCategories();
}