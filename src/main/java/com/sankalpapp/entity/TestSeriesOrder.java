package com.sankalpapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestSeriesOrder {

    @Id
    private String orderId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String orderStatus;

    private String customerEmail;

    private String customerPhone;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_series_id")
    private TestSeries testSeries;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @PrePersist
    public void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        if (orderStatus == null) {
            orderStatus = "CREATED";
        }
    }
}