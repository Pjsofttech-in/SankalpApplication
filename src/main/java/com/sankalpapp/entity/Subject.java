package com.sankalpapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "subjects")
@Data
@Getter
@Setter
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Math, English

    private Integer totalQuestions;

    private Integer totalMarks;

    private boolean active = true;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "test_series_id", nullable = false)
    private TestSeries testSeries;

    // One Subject -> Many Sections

    // @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    //@JsonIgnore
    //private List<Section> sections;

    /*@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Paper> papers;*/
}
