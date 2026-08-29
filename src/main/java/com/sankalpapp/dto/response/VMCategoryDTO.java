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
public class VMCategoryDTO {
    private Long id;
    private String categoryName;
    private String thumbnail;
    private LocalDate createdDate;
    private String materialTypeName;

}
