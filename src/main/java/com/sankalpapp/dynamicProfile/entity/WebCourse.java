package com.sankalpapp.dynamicProfile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String courseName;
    private String courseDescription;
    private String courseImage;
    private String courseColor;
    private String url;

    
    
    

    @ManyToOne
    @JoinColumn(name = "security_url_id")
    @JsonIgnore
    private WebSecurityUrl webSecurityUrl;
}

