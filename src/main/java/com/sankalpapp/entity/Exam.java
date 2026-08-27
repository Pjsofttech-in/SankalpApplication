package com.sankalpapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String examName;

    @Column(nullable = false)
    private LocalDate examDate;

    @Column(nullable = false)
    private Integer totalMarks;

    @Column(nullable = false)
    private Integer totalQuestions;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    @Builder.Default
    private Integer maxAttempts = 1;

    private LocalDate testStartDate;
    private LocalDate testEndDate;
    @Column(length = 5000)
    private String terms;
    private String image;

    @Builder.Default
    @Column(nullable = false)
    private Boolean resultFinalized = false;

    private Boolean downloadTestPaper;
    private Boolean showTestResult;
    private Boolean showAllResult;
    @Column(length = 1000)
    private String allResultPdf;

    private LocalTime startTime;  // 🕒 Stores when the test starts
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(
            mappedBy = "exam",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("sequence ASC")
    @Builder.Default
    private List<ExamQuestion> questions = new ArrayList<>();

    @OneToMany(
            mappedBy = "exam",
            cascade = CascadeType.ALL
    )
    @Builder.Default
    private List<TestSeriesExam> testSeriesExams = new ArrayList<>();

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (active == null) {
            active = true;
        }

        if (resultFinalized == null) {
            resultFinalized = false;
        }
    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}