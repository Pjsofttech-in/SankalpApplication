package com.sankalpapp.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalukaRequest {

    private String talukaName;

    private Long districtId;

    private Boolean active;
}