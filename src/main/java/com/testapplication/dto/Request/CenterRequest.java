package com.testapplication.dto.Request;

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
    private String taluka;
    private String district;
    private String state;
    private String pincode;

    private Long schoolId;
}