package com.testapplication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "schools")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String schoolName;

    @Column(nullable = false)
    private String principalName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String mobile;

    @Column(nullable = false)
    private String address;

    private String village;

    private String taluka;

    private String district;

    private String state;

    private String pincode;

    @Column(nullable = false)
    private Boolean active = true;

    // Login Account
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // One School -> Many Centers
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private List<Center> centers;

    // One School -> Many Coordinators
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private List<Coordinator> coordinators;

    // Student.java झाल्यावर हा relation enable करू
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private List<Student> students;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}