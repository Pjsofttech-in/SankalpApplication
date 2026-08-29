package com.sankalpapp.serviceimpl;

import com.sankalpapp.entity.VMMaterial;
import com.sankalpapp.entity.VMSubcategory;
import com.sankalpapp.repository.VMMaterialRepository;
import com.sankalpapp.repository.VMOrderRepository;
import com.sankalpapp.repository.VMSubcategoryRepository;
import com.sankalpapp.service.VMMaterialService;
import com.sankalpapp.util.SlugUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VMMaterialServiceImpl implements VMMaterialService {

    private static final String folder = "VMMaterial"; // Change to your actual S3 bucket
    @Autowired
    private VMMaterialRepository vmMaterialRepository;
    @Autowired
    private VMSubcategoryRepository vmSubcategoryRepository;
    @Autowired
    private VMOrderRepository vmOrderRepository;
    @Autowired
    private S3Client s3Client;
    @Autowired
    private S3Service s3Service;

    @Override
    public VMMaterial addMaterial(String materialType, Boolean saveToDevice,
                                  String status, Double mrp, Double price, Integer validity, String chapterName, String seo, String discription,
                                  Long subcategoryId, MultipartFile demoPdf, MultipartFile pdfFile, MultipartFile thumbnailFile) {

        VMMaterial vmMaterial = new VMMaterial();
        vmMaterial.setMaterialtype(materialType);
        vmMaterial.setSaveToDevice(saveToDevice);
        vmMaterial.setChapterName(chapterName);
        vmMaterial.setSlug(generateUniqueSlug(chapterName));
        vmMaterial.setStatus(status);
        vmMaterial.setMrp(mrp);
        vmMaterial.setPrice(price);
        vmMaterial.setValidity(validity);
        vmMaterial.setDiscription(discription);
        vmMaterial.setSeo(seo);

        // Set the subcategory
        if (subcategoryId != null) {
            Optional<VMSubcategory> foundSubcategory = vmSubcategoryRepository.findById(subcategoryId);
            foundSubcategory.ifPresent(vmSubcategory -> {
                vmMaterial.setVmSubcategory(vmSubcategory);
                vmMaterial.setSubcategoryName(vmSubcategory.getSubcategoryName());
                vmMaterial.setCategoryName(vmSubcategory.getVmCategory().getCategoryName());
            });
        }

        // Upload files to S3
        if (demoPdf != null && !demoPdf.isEmpty()) {
            vmMaterial.setDemoPdf(uploadFileToS3(demoPdf));
        }
        if (pdfFile != null && !pdfFile.isEmpty()) {
            vmMaterial.setPdfFile(uploadFileToS3(pdfFile));
        }
        if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
            vmMaterial.setThumbnailFile(uploadFileToS3(thumbnailFile));
        }

        vmMaterial.setCreatedDate(LocalDate.now());
        return vmMaterialRepository.save(vmMaterial);
    }

    private String uploadFileToS3(MultipartFile file) {
        try {
//            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//
//            s3Client.putObject(
//                    PutObjectRequest.builder()
//                            .bucket(bucketName)
//                            .key(fileName)
//                            .contentType(file.getContentType())
//                            .build(),
//                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()) // Streaming file
//            );
//
////            return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
//            return s3Service.generateFileUrl(fileName);
            return s3Service.uploadFile(file, folder);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
        }
    }

    private String generateUniqueSlug(String chapterName) {

        String slug = SlugUtil.generateSlug(chapterName);
        String uniqueSlug = slug;
        int count = 1;

        while (vmMaterialRepository.existsBySlug(uniqueSlug)) {
            uniqueSlug = slug + "-" + count;
            count++;
        }

        return uniqueSlug;
    }


    @Override
    public VMMaterial updateMaterial(Long vmMaterialId, String materialType, Boolean saveToDevice,
                                     String status, Double mrp, Double price, Integer validity, String chapterName, String seo, String discription,
                                     String subcategoryName, MultipartFile demoPdf, MultipartFile pdfFile, MultipartFile thumbnailFile) {

        Optional<VMMaterial> materialOptional = vmMaterialRepository.findById(vmMaterialId);
        if (materialOptional.isEmpty()) {
            throw new IllegalArgumentException("Material not found with id: " + vmMaterialId);
        }

        VMMaterial vmMaterial = materialOptional.get();
        vmMaterial.setMaterialtype(materialType);
        vmMaterial.setSaveToDevice(saveToDevice);
        vmMaterial.setChapterName(chapterName);
        vmMaterial.setSlug(SlugUtil.generateSlug(chapterName));
        vmMaterial.setStatus(status);
        vmMaterial.setSubcategoryName(subcategoryName);
        vmMaterial.setMrp(mrp);
        vmMaterial.setPrice(price);
        vmMaterial.setValidity(validity);
        vmMaterial.setDiscription(discription);
        vmMaterial.setSeo(seo);

        if (subcategoryName != null) {
            Optional<VMSubcategory> foundSubcategory = vmSubcategoryRepository.findBySubcategoryName(subcategoryName);
            foundSubcategory.ifPresent(vmSubcategory -> {
                vmMaterial.setVmSubcategory(vmSubcategory);
                vmMaterial.setSubcategoryName(vmSubcategory.getSubcategoryName());
                vmMaterial.setCategoryName(vmSubcategory.getVmCategory().getCategoryName());
            });
        }

        // Update files in S3
        if (demoPdf != null && !demoPdf.isEmpty()) {
            vmMaterial.setDemoPdf(uploadFileToS3(demoPdf));
        }
        if (pdfFile != null && !pdfFile.isEmpty()) {
            vmMaterial.setPdfFile(uploadFileToS3(pdfFile));
        }
        if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
            vmMaterial.setThumbnailFile(uploadFileToS3(thumbnailFile));
        }

        return vmMaterialRepository.save(vmMaterial);
    }

    @Override
    public VMMaterial getMaterialById(Long id) {
        return vmMaterialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material not found"));
    }

    @Override
    public List<VMMaterial> getAllMaterials() {
        return vmMaterialRepository.findAll();
    }

    @Override
    public void deleteMaterial(Long id) {
        vmMaterialRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> getReport() {
        Map<String, Object> reportData = new HashMap<>();

        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(7);
        LocalDate thirtyDaysAgo = today.minusDays(30);
        LocalDate oneYearAgo = today.minusDays(365);

        reportData.put("today", processResult(vmOrderRepository.findCountAndRevenueByDateRange(today, today)));
        reportData.put("last7Days", processResult(vmOrderRepository.findCountAndRevenueByDateRange(sevenDaysAgo, today)));
        reportData.put("last30Days", processResult(vmOrderRepository.findCountAndRevenueByDateRange(thirtyDaysAgo, today)));
        reportData.put("last365Days", processResult(vmOrderRepository.findCountAndRevenueByDateRange(oneYearAgo, today)));
        reportData.put("total", processResult(vmOrderRepository.findTotalCountAndRevenue()));

        return reportData;
    }


    private Map<String, Object> processResult(List<Object[]> result) {
        Map<String, Object> data = new HashMap<>();
        if (result == null || result.isEmpty()) {
            data.put("count", 0);
            data.put("revenue", 0.0);
        } else {
            Object[] row = result.getFirst();
            data.put("count", row[0] != null ? row[0] : 0);
            data.put("revenue", row[1] != null ? row[1] : 0.0);
        }
        return data;
    }

    @Override
    public List<Map<String, Object>> getMonthlyReport(int year) {
        LocalDate startDate = YearMonth.of(year, 1).atDay(1);
        LocalDate endDate = YearMonth.of(year, 12).atEndOfMonth();

        List<Object[]> results = vmOrderRepository.findMonthlyReport(startDate, endDate);

        return results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            Integer month = (Integer) row[0];
            map.put("month", Month.of(month).name()); // Optional: convert numeric month to name like JANUARY
            map.put("totalOrders", row[1] != null ? row[1] : 0);
            map.put("totalRevenue", row[2] != null ? row[2] : 0.0);
            return map;
        }).collect(Collectors.toList());
    }


    @Override
    public List<Map<String, Object>> getYearlyReport() {
        List<Object[]> results = vmOrderRepository.findYearlyReport();

        return results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("year", row[0]);
            map.put("totalOrders", row[1] != null ? row[1] : 0);
            map.put("totalRevenue", row[2] != null ? row[2] : 0.0);
            return map;
        }).collect(Collectors.toList());
    }


    @Override
    public Map<String, Object> compareTwoYears(int year1, int year2) {
        List<Object[]> results = vmOrderRepository.findComparisonData(year1, year2);
        Map<String, Object> comparison = new HashMap<>();

        for (Object[] row : results) {
            int year = (int) row[0];
            Map<String, Object> data = new HashMap<>();
            data.put("totalOrders", row[1] != null ? row[1] : 0);
            data.put("totalRevenue", row[2] != null ? row[2] : 0.0);
            comparison.put(String.valueOf(year), data);
        }

        // Handle missing year(s) with 0s
        if (!comparison.containsKey(String.valueOf(year1))) {
            Map<String, Object> data = new HashMap<>();
            data.put("totalOrders", 0);
            data.put("totalRevenue", 0.0);
            comparison.put(String.valueOf(year1), data);
        }
        if (!comparison.containsKey(String.valueOf(year2))) {
            Map<String, Object> data = new HashMap<>();
            data.put("totalOrders", 0);
            data.put("totalRevenue", 0.0);
            comparison.put(String.valueOf(year2), data);
        }

        return comparison;
    }


    @Override
    public List<Map<String, Object>> getCategoryRevenueByCategoryName(String categoryName) {
        List<Object[]> results = vmOrderRepository.findCategoryRevenueByCategoryName(categoryName);

        return results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("categoryName", row[0]);
            map.put("totalRevenue", row[1] != null ? row[1] : 0.0);
            return map;
        }).collect(Collectors.toList());
    }


    @Override
    public List<Map<String, Object>> getCategoryRevenue() {
        List<Object[]> results = vmOrderRepository.getAllCategoryRevenue();

        return results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("categoryName", row[0]);
            map.put("totalRevenue", row[1] != null ? row[1] : 0.0);
            return map;
        }).toList();
    }

    @Override
    public List<Map<String, Object>> getDailyCountAndRevenue(int year, int month) {
        List<Object[]> results = vmMaterialRepository.findDailyCountAndRevenue(year, month);
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> dayData = new HashMap<>();
            dayData.put("date", row[0]);
            dayData.put("count", row[1]);
            dayData.put("revenue", row[2]);
            response.add(dayData);
        }

        return response;
    }

    @Override
    public VMMaterial toggleDownloadButton(Long id) {
        Optional<VMMaterial> optional = vmMaterialRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Material not found with id: " + id);
        }

        VMMaterial material = optional.get();
        material.setDownloadButton(!material.getDownloadButton()); // toggle true/false
        return vmMaterialRepository.save(material);
    }

}
