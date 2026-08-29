package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.response.VMCategoryDTO;
import com.sankalpapp.entity.VMCategory;
import com.sankalpapp.entity.VMMaterialType;
import com.sankalpapp.repository.VMCategoryRepository;
import com.sankalpapp.repository.VMMaterialTypeRepository;
import com.sankalpapp.service.VMCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VMCategoryServiceImpl implements VMCategoryService {

    private static final String folder = "Category-Thumbnails";
    @Autowired
    private VMCategoryRepository vmCategoryRepository;
    @Autowired
    private VMMaterialTypeRepository vmMaterialTypeRepository;
    @Autowired
    private S3Service s3Service;


    @Override
    public VMCategory saveCategory(VMCategory category, MultipartFile thumbnailFile, Long materialtype_id) {
        try {
            // Fetch MaterialType by id
            VMMaterialType vmMaterialType = vmMaterialTypeRepository.findById(materialtype_id)
                    .orElseThrow(() -> new RuntimeException("MaterialType not found with id: " + materialtype_id));

            category.setVmMaterialType(vmMaterialType);

            // Construct S3 file URL (without baseUrl)
//            String thumbnailUrl = "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
            String thumbnailUrl = s3Service.uploadFile(thumbnailFile, folder);
            category.setThumbnail(thumbnailUrl);
            category.setCreatedDate(LocalDate.now());

            return vmCategoryRepository.save(category);
        } catch (IOException e) {
            throw new RuntimeException("Error while uploading the thumbnail image: " + e.getMessage());
        }
    }


    @Override
    public VMCategory updateCategory(Long id, VMCategory vmCategory, MultipartFile thumbnailFile) {
        VMCategory existingCategory = vmCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        try {
            // If a new thumbnail file is provided, upload it to S3
            if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
                // Construct S3 file URL (without baseUrl)
//                String thumbnailUrl = "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
                String thumbnailUrl = s3Service.uploadFile(thumbnailFile, folder);
                existingCategory.setThumbnail(thumbnailUrl);
            }

            // Update other fields
            existingCategory.setCategoryName(vmCategory.getCategoryName());

            return vmCategoryRepository.save(existingCategory);
        } catch (IOException e) {
            throw new RuntimeException("Error while uploading the thumbnail image: " + e.getMessage());
        }
    }

    @Override
    public void deleteCategory(Long id) {
        vmCategoryRepository.deleteById(id);
    }

    @Override
    public List<VMCategoryDTO> getAllCategories() {
        List<VMCategory> vmCategories = vmCategoryRepository.findAllWithMaterialType();
        return vmCategories.stream()
                .map(vmCategory -> new VMCategoryDTO(
                        vmCategory.getId(),
                        vmCategory.getCategoryName(),
                        vmCategory.getThumbnail(),
                        vmCategory.getCreatedDate(),
                        vmCategory.getMaterialTypeName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public VMCategory getCategoryById(Long id) {
        return vmCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    @Override
    public Optional<VMCategory> findCategoryByName(String categoryName) {
        return vmCategoryRepository.findCategoryByName(categoryName);
    }

    @Override
    public List<VMCategory> getCategoriesByDateRange(String filter, String startDateStr, String endDateStr) {
        LocalDate startDate;
        LocalDate endDate = LocalDate.now();

        if (startDateStr != null && endDateStr != null) {
            try {
                startDate = LocalDate.parse(startDateStr);
                endDate = LocalDate.parse(endDateStr);
            } catch (DateTimeException e) {
                throw new IllegalArgumentException("Invalid date format. Please use 'yyyy-MM-dd'.");
            }
        } else if (filter != null) {
            switch (filter.toLowerCase()) {
                case "7":
                    startDate = endDate.minusDays(7);
                    break;
                case "30":
                    startDate = endDate.minusDays(30);
                    break;
                case "today":
                    startDate = endDate;
                    break;
                case "365":
                    startDate = endDate.minusDays(365);
                    break;
                case "total":
                    startDate = LocalDate.of(2000, 1, 1);
                    endDate = LocalDate.of(2099, 12, 31); // Return all
                    break;
                default:
                    throw new IllegalArgumentException("Invalid filter. Please use '7', '30', 'today', '365', 'total', or provide custom dates.");
            }
        } else {
            throw new IllegalArgumentException("Please provide either 'filter' or both 'startDate' and 'endDate'.");
        }

        return vmCategoryRepository.findByCreatedDateBetween(startDate, endDate);
    }

    @Override
    public List<VMCategoryDTO> getCategoriesByMaterialTypeName(String materialTypeName) {
        List<VMCategory> vmCategories = vmCategoryRepository.findByMaterialTypeName(materialTypeName);
        return vmCategories.stream()
                .map(vmCategory -> new VMCategoryDTO(
                        vmCategory.getId(),
                        vmCategory.getCategoryName(),
                        vmCategory.getThumbnail(),
                        vmCategory.getCreatedDate(),
                        vmCategory.getMaterialTypeName()))
                .collect(Collectors.toList());
    }
}
