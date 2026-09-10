package com.sankalpapp.dynamicProfile.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "exam_section")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamSection {

    @Id
    private Long id = 1L;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "exam_title", nullable = false)
    private String examTitle;

    @Column(name = "exam_date", nullable = false)
    private LocalDate examDate;

    @Column(name = "application_closing_date", nullable = false)
    private LocalDate applicationClosingDate;

    @Column(name = "registration_fee", nullable = false)
    private Double registrationFee;

    @Column(name = "eligible_classes", nullable = false, columnDefinition = "TEXT")
    private String eligibleClasses;

    @Column(name = "exam_pattern", nullable = false, columnDefinition = "TEXT")
    private String examPattern;

    @Column(name = "centers", nullable = false, columnDefinition = "TEXT")
    private String centers;
}