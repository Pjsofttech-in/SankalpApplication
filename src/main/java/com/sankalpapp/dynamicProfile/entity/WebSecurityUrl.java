package com.sankalpapp.dynamicProfile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebSecurityUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    
    
    

    @OneToMany(mappedBy = "webSecurityUrl", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WebManuBar> webManuBars;

    @OneToMany(mappedBy = "webSecurityUrl", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WebAboutUs> webAboutUses;

    @OneToMany(mappedBy = "webSecurityUrl", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WebAwardsAndAccolades> webAwardsAndAccolades;

    @OneToMany(mappedBy = "webSecurityUrl" , cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WebCourse> webCourse;

    @OneToMany(mappedBy = "webSecurityUrl", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WebFaculty> webFaculty;

    @OneToMany(mappedBy = "webSecurityUrl", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WebFooter> webFooter;

    @OneToMany(mappedBy = "webSecurityUrl", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WebContactForm> webContactForms;

    @OneToMany(mappedBy = "webSecurityUrl", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WebFacultyTitle> webFacultyTitles;

    @OneToMany(mappedBy = "webSecurityUrl", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WebCounter> webCounters;

    @OneToMany(mappedBy = "webSecurityUrl", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WebMapAndImages> webMapAndImages;

    @OneToMany(mappedBy = "webSecurityUrl", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WebTestimonials> testimonials;

    @OneToMany(mappedBy = "webSecurityUrl", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WebVisionMission> webVisionMissions;

    @OneToMany(mappedBy = "webSecurityUrl", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WebJobCareerOption> webJobCareerOptions;

    @OneToMany(mappedBy = "webSecurityUrl", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WebHRDetails> webHRDetails;

    @OneToMany(mappedBy = "webSecurityUrl", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WebGallery> galleries;

    @OneToMany(mappedBy = "webSecurityUrl", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WebSlideBar> webSlideBars;

    @OneToMany(mappedBy = "webSecurityUrl", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WebTopper> webToppers;
}

