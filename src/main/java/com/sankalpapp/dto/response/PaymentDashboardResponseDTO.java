package com.sankalpapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDashboardResponseDTO {

    private String type;

    private List<PaymentGraphDTO> data;
}