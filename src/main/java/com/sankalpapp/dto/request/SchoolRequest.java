package com.sankalpapp.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolRequest {

    private String schoolName;
    private String principalName;
    private String email;
    private String mobile;
    private String address;
    private String village;
    private String taluka;
    private String district;
    private String state;
    private String pincode;

    private Long userId;
}