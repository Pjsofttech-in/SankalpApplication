package com.sankalpapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    // Login Account
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    // Many Schools -> One Center
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "center_id")
    private Center center;

    // One School -> Many Coordinators
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Coordinator> coordinators;

    // One School -> Many Students
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Student> students;

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