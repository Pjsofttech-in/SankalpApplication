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
    private String taluka;
    private String district;
    private String state;
    private String pincode;
    private String schoolName;
    private Boolean active;
}