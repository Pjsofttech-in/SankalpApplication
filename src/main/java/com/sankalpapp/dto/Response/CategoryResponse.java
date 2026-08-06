package com.sankalpapp.dto.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {

    private Long id;
    private String categoryName;
    private String description;
    private Boolean active;
}