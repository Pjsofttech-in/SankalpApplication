package com.sankalpapp.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestSeriesPurchaseResponse {

    private String orderId;

    private Long testSeriesId;

    private String testSeriesName;

    private Double amount;

    private String orderStatus;

    private String paymentStatus;

    private String razorpayOrderId;
}