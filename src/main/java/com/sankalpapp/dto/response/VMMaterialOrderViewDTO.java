package com.sankalpapp.dto.response;

import lombok.Data;

@Data
public class VMMaterialOrderViewDTO {
    private Long id;
    private String materialtype;
    private String pdfFile;
    private String thumbnailFile;
    private Boolean saveToDevice;
    private String demoPdf;
    private String status;
    private Double mrp;
    private Double price;
    private String chapterName;
    private Integer validity;
    private String discription;
    private String categoryName;
    private String subcategoryName;
}