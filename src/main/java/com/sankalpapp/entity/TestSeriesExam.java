package com.sankalpapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "test_series_exams",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"test_series_id", "exam_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestSeriesExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_series_id", nullable = false)
    private TestSeries testSeries;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @Column(nullable = false)
    private Integer sequence;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;
}