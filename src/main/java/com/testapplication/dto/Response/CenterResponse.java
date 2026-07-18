package com.testapplication.dto.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CenterResponse {

    private Long id;

    private String centerName;
    private String centerCode;
    private String address;
    private String village;
    private String state;
    private String pincode;

    private Boolean active;

    private Long schoolId;
    private String schoolName;

    private Long districtId;
    private String districtName;

    private Long talukaId;
    private String talukaName;
}