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
public class WebVisionMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vision", nullable = false, length = 1500)
    private String vision;

    @Column(name = "mission", nullable = false, length = 1500)
    private String mission;

    private String visionmissionColor;

    @Column(name = "directorMessage", length = 2000)
    private String directorMessage;

    @Column(name = "directorName", length = 255)
    private String directorName;

    @Column(name = "directorImage")
    private String directorImage;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String url;

    
    
    

    @ManyToOne
    @JoinColumn(name = "security_url_id")
    @JsonIgnore
    private WebSecurityUrl webSecurityUrl;
}
