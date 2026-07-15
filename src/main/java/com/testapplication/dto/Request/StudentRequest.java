package com.testapplication.dto.Request;

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
    private String gender;
    private String studentClass;
    private String medium;
    private String address;
    private String village;
    private String taluka;
    private String district;
    private String state;
    private String pincode;
    private String email;
    private LocalDate dateOfBirth;

    private Long schoolId;
    private Long centerId;
    private Long coordinatorId;
    private Long userId;
}