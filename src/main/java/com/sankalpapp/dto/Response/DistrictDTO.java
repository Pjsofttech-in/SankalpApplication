package com.sankalpapp.dto.Response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistrictDTO {

    private Long id;

    private String districtName;

    private Boolean active;

    private List<TalukaDTO> talukaList;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}