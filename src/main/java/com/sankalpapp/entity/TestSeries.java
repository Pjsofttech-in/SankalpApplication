package com.sankalpapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "test_series")
@Data
@Getter
@Setter
public class TestSeries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // SSC CGL, SSC

    private String examType; // T1, T2


    private Integer durationMinutes;

    private boolean active = true;

    private Long mrp;

    private Integer price;

    private String description;

    private  String features;

    private String testseriesImageUrl;

    private LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "testSeries", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Subject> subjects;
}
