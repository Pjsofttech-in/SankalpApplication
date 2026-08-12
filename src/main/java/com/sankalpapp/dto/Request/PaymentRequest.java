package com.sankalpapp.dto.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    private Double amount;

    private String paymentMode;

    private String transactionId;

    private String paymentStatus;

    private Long studentId;

    private String orderId;

    private String paymentId;

    private String signature;
}


