package com.sankalpapp.controller;

import com.sankalpapp.dto.response.VMCategoryDTO;
import com.sankalpapp.entity.VMCategory;
import com.sankalpapp.service.VMCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://vartmannirnay.com")
public class VMCategoryController {

    @Autowired
    private VMCategoryService vmCategoryService;

    @PostMapping("/createVMCategory")
    public VMCategory createCategory(@RequestParam("name") String categoryName,
                                     @RequestParam("createdDate") String createdDateStr,
                                     @RequestParam("materialtype_id") Long materialtype_id,
                                     @RequestParam("thumbnail") MultipartFile thumbnail) throws IOException {
        LocalDate createdDate = LocalDate.parse(createdDateStr);

        VMCategory vmCategory = new VMCategory();
        vmCategory.setCategoryName(categoryName);
        vmCategory.setCreatedDate(createdDate);

        // Call service to save the category
        return vmCategoryService.saveCategory(vmCategory, thumbnail, materialtype_id);
    }

    @GetMapping("/VMCategoryById/{id}")
    public VMCategory getCategoryById(@PathVariable Long id) {
        return vmCategoryService.getCategoryById(id);
    }

    @GetMapping("/AllVMCategories")
    public List<VMCategoryDTO> getAllCategories() {
        return vmCategoryService.getAllCategories();
    }

    @PutMapping("/updateVMCategory/{id}")
    public VMCategory updateCategory(@PathVariable Long id,
                                     @RequestParam("name") String categoryName,
                                     @RequestParam(value = "createdDate", required = false) String createdDateStr,
                                     @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail) throws IOException {
        LocalDate createdDate = (createdDateStr != null) ? LocalDate.parse(createdDateStr) : null;

        VMCategory existingCategory = vmCategoryService.getCategoryById(id);

        existingCategory.setCategoryName(categoryName);
        if (createdDate != null) {
            existingCategory.setCreatedDate(createdDate);
        }

        return vmCategoryService.updateCategory(id, existingCategory, thumbnail);
    }

    @DeleteMapping("/deleteVMCategory/{id}")
    public void deleteCategory(@PathVariable Long id) {
        vmCategoryService.deleteCategory(id);
    }

    @GetMapping("/searchByVMcategoryName")
    public Optional<VMCategory> searchByCategoryName(@RequestParam String categoryName) {
        return vmCategoryService.findCategoryByName(categoryName);
    }


    @GetMapping("/VMCategoriesByDateRange")
    public List<VMCategory> getCategoriesByDateRange(
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {

        return vmCategoryService.getCategoriesByDateRange(filter, startDateStr, endDateStr);
    }

    @GetMapping("/VMCategoriesByMaterialType")
    public List<VMCategoryDTO> getCategoriesByMaterialType(@RequestParam("materialTypeName") String materialTypeName) {
        return vmCategoryService.getCategoriesByMaterialTypeName(materialTypeName);
    }

}
