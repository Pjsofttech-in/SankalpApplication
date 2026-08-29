package com.sankalpapp.service;

import com.sankalpapp.entity.VMMaterial;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface VMMaterialService {
    VMMaterial addMaterial(String materialType, Boolean saveToDevice,
                           String status, Double mrp, Double price, Integer validity, String chapterName, String seo, String discription,
                           Long subcategoryId, MultipartFile demoPdf, MultipartFile pdfFile, MultipartFile thumbnailFile);

    VMMaterial updateMaterial(Long vmMaterialId, String materialType, Boolean saveToDevice,
                              String status, Double mrp, Double price, Integer validity, String chapterName, String seo, String discription,
                              String subcategoryName, MultipartFile demoPdf, MultipartFile pdfFile, MultipartFile thumbnailFile);

    VMMaterial getMaterialById(Long id);

    List<VMMaterial> getAllMaterials();

    void deleteMaterial(Long id);


    Map<String, Object> getReport();

    List<Map<String, Object>> getMonthlyReport(int year);

    List<Map<String, Object>> getYearlyReport();

    Map<String, Object> compareTwoYears(int year1, int year2);

    List<Map<String, Object>> getCategoryRevenueByCategoryName(String categoryName);

    List<Map<String, Object>> getCategoryRevenue();

    List<Map<String, Object>> getDailyCountAndRevenue(int year, int month);

    VMMaterial toggleDownloadButton(Long id);

}

