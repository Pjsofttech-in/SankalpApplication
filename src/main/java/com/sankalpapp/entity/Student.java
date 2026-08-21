package com.sankalpapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String studentName;

    @Column(nullable = false)
    private String fatherName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true, length = 10)
    private String mobile;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String studentClass;

    @Column(nullable = false)
    private String medium;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String village;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String pincode;

    @Column(nullable = false)
    private String school;

    private LocalDate dateOfBirth;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    // Payment
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    // Login User
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // District
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    // Taluka
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taluka_id", nullable = false)
    private Taluka taluka;

    // Center
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id", nullable = false)
    private Center center;

    // Coordinator
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordinator_id", nullable = false)
    private Coordinator coordinator;

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