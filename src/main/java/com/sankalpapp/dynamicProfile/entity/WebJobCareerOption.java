package com.sankalpapp.dynamicProfile.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebJobCareerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String location;
    private String salaryRange;
//    private String JobCareerOptionColor;
    @Column(length = 500)
    private String responsibilities;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate postDate;
    private String resumeUrl;
    private String lastDateToApply;
    private String url;
    private String jobColour;
    private String jobVacancy;

    
    
    

    @ManyToOne
    @JoinColumn(name = "security_url_id")
    @JsonIgnore
    private WebSecurityUrl webSecurityUrl;

    @ManyToOne
    @JoinColumn(name ="Web_Hr_Details_id")
//    @JsonIgnore
    private WebHRDetails webHRDetails;
}
