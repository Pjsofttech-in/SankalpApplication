package com.sankalpapp.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VMMaterialPurchaseResponse {

    private String orderId;

    private Long materialId;

    private String materialName;

    private Double amount;

    private String orderStatus;

    private String paymentStatus;

    private String razorpayOrderId;
}