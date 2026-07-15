package com.testapplication.serviceimpl;

import com.testapplication.entity.Category;
import com.testapplication.repository.CategoryRepository;
import com.testapplication.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {

        if (categoryRepository.findByCategoryName(category.getCategoryName()).isPresent()) {
            throw new RuntimeException("Category already exists.");
        }

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, Category category) {

        Category existing = getCategoryById(id);

        existing.setCategoryName(category.getCategoryName());
        existing.setDescription(category.getDescription());
        existing.setActive(category.getActive());

        return categoryRepository.save(existing);
    }

    @Override
    public void deleteCategory(Long id) {

        categoryRepository.delete(getCategoryById(id));
    }

    @Override
    public Category getCategoryById(Long id) {

        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found."));
    }

    @Override
    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }
}