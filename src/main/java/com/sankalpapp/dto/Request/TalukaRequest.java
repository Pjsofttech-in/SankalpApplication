package com.sankalpapp.dto.Request;

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