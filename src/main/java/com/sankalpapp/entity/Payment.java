package com.sankalpapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Student
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Amount
    @Column(nullable = false)
    private Double amount;

    // Razorpay Order Id
    @Column(unique = true)
    private String orderId;

    // Razorpay Payment Id
    @Column(unique = true)
    private String paymentId;

    // Transaction Id
    @Column(unique = true)
    private String transactionId;

    // UPI / Card / NetBanking
    private String paymentMode;

    // SUCCESS / FAILED / PENDING
    @Column(nullable = false)
    private String paymentStatus;

    private LocalDateTime paymentDate;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        if (active == null) {
            active = true;
        }

        if (paymentDate == null) {
            paymentDate = LocalDateTime.now();
        }

        if (paymentStatus == null) {
            paymentStatus = "PENDING";
        }

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}