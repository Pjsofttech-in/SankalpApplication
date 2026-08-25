package com.sankalpapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "exam_questions",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"exam_id", "question_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false)
    private Integer sequence;

    @Column(nullable = false)
    private Integer marks;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;
}