package com.sankalpapp.dto.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CenterRequest {

    private String centerName;
    private String centerCode;
    private String address;
    private String village;
    private String state;
    private String pincode;
    private Boolean active;

    private Long schoolId;
    private Long districtId;
    private Long talukaId;
}