package com.sankalpapp.dto.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GalleryRequest {

    private String title;
    private String description;
    private String imageUrl;
    private String category;
    private Integer displayOrder;
    private Boolean active;
}