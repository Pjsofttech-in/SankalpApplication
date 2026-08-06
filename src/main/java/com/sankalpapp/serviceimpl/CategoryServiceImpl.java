package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.Request.CategoryRequest;
import com.sankalpapp.dto.Response.CategoryResponse;
import com.sankalpapp.entity.Category;
import com.sankalpapp.repository.CategoryRepository;
import com.sankalpapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse saveCategory(CategoryRequest request) {

        if (categoryRepository.findByCategoryName(request.getCategoryName()).isPresent()) {
            throw new RuntimeException("Category already exists.");
        }

        Category category = Category.builder()
                .categoryName(request.getCategoryName())
                .description(request.getDescription())
                .active(true)
                .build();

        return mapToResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found."));

        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());

        return mapToResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found."));

        categoryRepository.delete(category);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found."));

        return mapToResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CategoryResponse mapToResponse(Category category) {

        return CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .active(category.getActive())
                .build();
    }
}