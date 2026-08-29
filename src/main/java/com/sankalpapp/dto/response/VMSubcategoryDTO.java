package com.sankalpapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class VMSubcategoryDTO {
    private Long id;
    private String subcategoryName;
    private LocalDate createdDate;
    private String categoryName;
    private Long categoryId; // To map category
    private List<Long> materialIds;
}