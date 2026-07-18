package com.testapplication.dto.Response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Long id;

    private Double amount;

    private String orderId;

    private String paymentId;

    private String transactionId;

    private String paymentMode;

    private String paymentStatus;

    private LocalDateTime paymentDate;

    private Boolean active;

    private Long studentId;

    private String studentName;
}