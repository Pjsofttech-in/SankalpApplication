package com.sankalpapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test_series")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestSeries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String image;

    private Double price;

    private Double sellingPrice;
    private Double mrp;
    private String testFeatureOne;
    private String testFeatureTwo;
    private String testFeatureThree;
    private String subject;
    @Column(length = 1000)
    private String seo;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(
            mappedBy = "testSeries",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("sequence ASC")
    @Builder.Default
    private List<TestSeriesExam> exams = new ArrayList<>();

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