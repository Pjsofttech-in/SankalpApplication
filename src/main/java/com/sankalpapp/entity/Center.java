package com.sankalpapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "centers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Center {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String centerName;

    @Column(nullable = false, unique = true)
    private String centerCode;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String village;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String pincode;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    // School
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "school_id", nullable = false)
    @JsonIgnoreProperties({"centers", "coordinators", "students"})
    private School school;

    // District
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "district_id", nullable = false)
    @JsonIgnoreProperties({"talukas"})
    private District district;

    // Taluka
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "taluka_id", nullable = false)
    @JsonIgnoreProperties({"district", "centers"})
    private Taluka taluka;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        if (active == null) {
            active = true;
        }

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}