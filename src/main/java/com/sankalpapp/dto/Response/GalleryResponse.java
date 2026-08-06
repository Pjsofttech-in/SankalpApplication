package com.sankalpapp.dto.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GalleryResponse {

    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private String category;
    private Integer displayOrder;
    private Boolean active;
}