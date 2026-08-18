package com.sankalpapp.dto.Response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {

    private Long id;

    private String studentName;
    private String mobile;
    private String email;
    private String gender;
    private String studentClass;
    private String medium;
    private String address;
    private String village;
    private String state;
    private String pincode;
    private LocalDate dateOfBirth;

    private Boolean active;

    private Long userId;
    private String userName;

    private String schoolName;

    private Long districtId;
    private String districtName;

    private Long talukaId;
    private String talukaName;

    private Long centerId;
    private String centerName;

    private Long coordinatorId;
    private String coordinatorName;

    private boolean isPaymentDone;
}