package com.sankalpapp.controller;

import com.sankalpapp.dto.response.VMSubcategoryDTO;
import com.sankalpapp.service.VMSubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://vartmannirnay.com")
public class VMSubcategoryController {

    @Autowired
    private VMSubcategoryService vmSubcategoryService;


    @PostMapping("/createVMSubCategory")
    public ResponseEntity<VMSubcategoryDTO> createSubCategory(@RequestBody VMSubcategoryDTO vmSubcategoryDTO) {
        VMSubcategoryDTO savedSubcategory = vmSubcategoryService.save(vmSubcategoryDTO);
        return ResponseEntity.ok(savedSubcategory);
    }

    @GetMapping("/VMSubCategoryById/{id}")
    public ResponseEntity<VMSubcategoryDTO> getSubCategoryById(@PathVariable Long id) {
        return vmSubcategoryService.findById(id)
                .map(vmSubcategory -> {
                    VMSubcategoryDTO dto = new VMSubcategoryDTO();
                    dto.setId(vmSubcategory.getId());
                    dto.setSubcategoryName(vmSubcategory.getSubcategoryName());
                    dto.setCategoryId(vmSubcategory.getVmCategory().getId());
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/AllVMSubCategories")
    public ResponseEntity<List<VMSubcategoryDTO>> getAllSubCategories() {
        List<VMSubcategoryDTO> subCategories = vmSubcategoryService.findAll().stream()
                .map(vmSubcategory -> {
                    VMSubcategoryDTO dto = new VMSubcategoryDTO();
                    dto.setId(vmSubcategory.getId());
                    dto.setSubcategoryName(vmSubcategory.getSubcategoryName());
                    dto.setCategoryId(vmSubcategory.getVmCategory().getId());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(subCategories);
    }

    @PutMapping("/updateVMSubCategory/{id}")
    public ResponseEntity<VMSubcategoryDTO> updateSubCategory(@PathVariable Long id, @RequestBody VMSubcategoryDTO vmSubcategoryDTO) {
        VMSubcategoryDTO updatedSubCategory = vmSubcategoryService.updateSubCategory(id, vmSubcategoryDTO);
        return ResponseEntity.ok(updatedSubCategory);
    }

    @DeleteMapping("/deleteVMSubcategory/{id}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable Long id) {
        boolean deleted = vmSubcategoryService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/searchByVMSubcategory")
    public ResponseEntity<VMSubcategoryDTO> searchBySubcategoryName(@RequestParam String subcategoryName) {
        return vmSubcategoryService.findSubcategoryByName(subcategoryName)
                .map(vmSubcategory -> {
                    VMSubcategoryDTO dto = new VMSubcategoryDTO();
                    dto.setId(vmSubcategory.getId());
                    dto.setSubcategoryName(vmSubcategory.getSubcategoryName());
                    dto.setCategoryId(vmSubcategory.getVmCategory().getId());
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/VMsubcategoryByCategory")
    public ResponseEntity<List<VMSubcategoryDTO>> getSubcategoriesByCategory(@RequestParam String categoryName) {
        List<VMSubcategoryDTO> subcategories = vmSubcategoryService.getSubcategoriesByCategory(categoryName).stream()
                .map(vmSubcategory -> {
                    VMSubcategoryDTO dto = new VMSubcategoryDTO();
                    dto.setId(vmSubcategory.getId());
                    dto.setSubcategoryName(vmSubcategory.getSubcategoryName());
                    dto.setCategoryId(vmSubcategory.getVmCategory().getId());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(subcategories);
    }


    @GetMapping("/VMSubcategorybycategoryname")
    public ResponseEntity<List<VMSubcategoryDTO>> getSubcategoryByCategoryName(@RequestParam String categoryName) {
        List<VMSubcategoryDTO> subcategories = vmSubcategoryService.getSubcategoryByCategoryName(categoryName);
        if (subcategories.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(subcategories);
        }
    }
}
