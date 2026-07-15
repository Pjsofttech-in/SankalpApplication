package com.testapplication.service;

import com.testapplication.entity.Category;

import java.util.List;

public interface CategoryService {

    Category saveCategory(Category category);

    Category updateCategory(Long id, Category category);

    void deleteCategory(Long id);

    Category getCategoryById(Long id);

    List<Category> getAllCategories();
}