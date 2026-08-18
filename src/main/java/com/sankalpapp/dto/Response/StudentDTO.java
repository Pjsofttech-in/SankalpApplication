package com.sankalpapp.dto.Response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {

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

    private String school;

    private LocalDate dateOfBirth;

    private Boolean active;

    private Long districtId;

    private String districtName;

    private Long talukaId;

    private String talukaName;

    private Long centerId;

    private String centerName;

    private Long coordinatorId;

    private String coordinatorName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean isPaymentDone;
}