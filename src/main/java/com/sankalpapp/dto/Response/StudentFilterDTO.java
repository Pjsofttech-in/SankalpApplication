package com.sankalpapp.dto.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentFilterDTO {

    private Long districtId;

    private Long talukaId;

    private Long centerId;

    private String school;

    private String studentClass;

    private String medium;

    private String gender;

    private Boolean active;

    private String search;
}