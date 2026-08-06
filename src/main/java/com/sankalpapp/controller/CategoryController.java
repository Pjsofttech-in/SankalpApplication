package com.sankalpapp.controller;

import com.sankalpapp.dto.Request.CategoryRequest;
import com.sankalpapp.dto.Response.CategoryResponse;
import com.sankalpapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CategoryService categoryService;

    // Save Category
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public CategoryResponse saveCategory(@RequestBody CategoryRequest request) {

        return categoryService.saveCategory(request);
    }

    // Get All Categories
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public List<CategoryResponse> getAllCategories() {

        return categoryService.getAllCategories();
    }

    // Get Category By Id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public CategoryResponse getCategoryById(@PathVariable Long id) {

        return categoryService.getCategoryById(id);
    }

    // Update Category
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CategoryResponse updateCategory(@PathVariable Long id,
                                           @RequestBody CategoryRequest request) {

        return categoryService.updateCategory(id, request);
    }

    // Delete Category
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteCategory(@PathVariable Long id) {

        categoryService.deleteCategory(id);

        return "Category deleted successfully.";
    }
}