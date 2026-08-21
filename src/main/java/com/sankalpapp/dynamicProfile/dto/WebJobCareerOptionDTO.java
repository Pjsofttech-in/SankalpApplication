package com.sankalpapp.dynamicProfile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebJobCareerOptionDTO {
    private Long id;
    private String title;
    private String description;
    private String location;
    private String salaryRange;
    private String responsibilities;
    private LocalDate postDate;
    private String resumeUrl;
    private String lastDateToApply;
    private String jobVacancy;
    private String url;
    private String jobColour;
    private String createdByEmail;
    
    

    private Long webHRDetailsId;
}