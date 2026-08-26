package com.sankalpapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "student_answers",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_attempt_question",
                        columnNames = {
                                "attempt_id",
                                "question_id"
                        }
                )
        }
)
public class StudentAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String selectedAnswer;

    @Builder.Default
    @Column(nullable = false)
    private Boolean correct = false;

    @Column(nullable = false)
    @Builder.Default
    private Integer marksObtained = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id", nullable = false)
    private ExamAttempt attempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(columnDefinition = "TEXT")  // ✅ Store long text explanation
    private String answerExplanation;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {

        if (correct == null) {
            correct = false;
        }

        if (marksObtained == null) {
            marksObtained = 0;
        }

        createdAt = LocalDateTime.now();
    }
}