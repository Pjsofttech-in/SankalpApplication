package com.sankalpapp.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalukaDTO {

    private Long id;

    private String talukaName;

    private Long districtId;

    private String districtName;

    private Boolean active;

    private List<Long> centerIds;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}