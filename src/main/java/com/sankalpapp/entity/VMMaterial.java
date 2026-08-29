package com.sankalpapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class VMMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(unique = true, length = 500)
    private String slug;
    private Integer validity;
    @Column(length = 1000)
    private String seo;
    @Column(length = 4000)
    private String discription;
    private LocalDate createdDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "subcategory_id", nullable = false)
    @JsonIgnore
    private VMSubcategory vmSubcategory;

    @OneToMany(mappedBy = "vmMaterial")
    @JsonIgnore
    private List<VMUserMaterialAssociation> vmUserMaterialAssociations;

    private String categoryName;
    private String subcategoryName;
    private Boolean downloadButton = false;

    @JsonProperty("pdfFile")
    public String getVisiblePdfFile() {
        if ("free".equalsIgnoreCase(this.status)) {
            return this.pdfFile;
        }
        return null;
    }

}
