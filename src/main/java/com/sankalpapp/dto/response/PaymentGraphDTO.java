package com.sankalpapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentGraphDTO {

    private String period;

    private Long paymentCount;

    private Double totalAmount;
}