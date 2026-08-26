package com.sankalpapp.dynamicProfile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebContactForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be exactly 10 digits")
    private String mobileNo;
    private String course;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Email
    private String email;
    private  String academicYear;
    private String url;

    
    
    

    @ManyToOne
    @JoinColumn(name = "security_url_id")
    @JsonIgnore
    private WebSecurityUrl webSecurityUrl;
}
