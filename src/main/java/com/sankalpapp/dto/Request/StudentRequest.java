package com.sankalpapp.dto.Request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRequest {

    private String studentName;
    private String mobile;
    private String email;
    private String password;
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
    private Long schoolId;
    private Long districtId;
    private Long talukaId;
    private Long centerId;
    private Long coordinatorId;
}