package com.sankalpapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class VMMaterialDTO {
    private Long id;
    private String materialtype;
    private String materialName;
    private String pdfFile;
    private String thumbnailFile;
    private Boolean saveToDevice;
    private String demoPdf;
    private String status;
    private String chapterName;
    private Double mrp;
    private Double price;
    private Integer validity;
    private LocalDate createdDate;
    private Long subcategoryId; // ID of the subcategory
    private String subcategoryName; // Name of the subcategory
    private String categoryName; // Name of the category
    private double paidAmount;

    public VMMaterialDTO(Long id, String materialtype, String pdfFile, String thumbnailFile,
                         boolean saveToDevice, String demoPdf, String status, double mrp,
                         double price, String chapterName, int validity, LocalDate createdDate,
                         String categoryName, String subcategoryName, double paidAmount) {
        this.id = id;
        this.materialtype = materialtype;
        this.pdfFile = pdfFile;
        this.thumbnailFile = thumbnailFile;
        this.saveToDevice = saveToDevice;
        this.demoPdf = demoPdf;
        this.status = status;
        this.mrp = mrp;
        this.price = price;
        this.chapterName = chapterName;
        this.validity = validity;
        this.createdDate = createdDate;
        this.categoryName = categoryName;
        this.subcategoryName = subcategoryName;
        this.paidAmount = paidAmount; // Initialize paidAmount
    }


}
