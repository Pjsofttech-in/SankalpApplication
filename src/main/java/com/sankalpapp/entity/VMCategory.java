package com.sankalpapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class VMCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    private String thumbnail;
    private LocalDate createdDate = LocalDate.now();

    @OneToMany(mappedBy = "vmCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<VMSubcategory> vmSubcategories;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "materialType_id")
    private VMMaterialType vmMaterialType;

    @Transient
    public String getMaterialTypeName() {
        return vmMaterialType != null ? vmMaterialType.getMaterialtype() : null;
    }


}

