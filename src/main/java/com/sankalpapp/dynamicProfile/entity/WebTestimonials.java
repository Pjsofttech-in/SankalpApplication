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
public class WebTestimonials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testimonialId;

    private String testimonialTitle;
    private String testimonialName;
    private String exam;
    private String post;
    @Column(name = "`rank`")
    private String rank;
    private String testimonialImage;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String testimonialColor;
    private String url;

    
    
    

    @ManyToOne
    @JoinColumn(name = "security_url_id")
    @JsonIgnore
    private WebSecurityUrl webSecurityUrl;
}
