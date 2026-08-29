package com.sankalpapp.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CenterDTO {

    private Long id;

    private String centerName;

    private String centerCode;

    private String address;

    private String village;

    private String state;

    private String pincode;

    private Boolean active;

    private Long districtId;

    private String districtName;

    private Long talukaId;

    private String talukaName;

    private Long coordinatorId;

    private String coordinatorName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}