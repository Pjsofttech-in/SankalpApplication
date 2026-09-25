package com.sankalpapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestSeriesGraphDTO {

    private String period;

    private Long orderCount;

    private Double totalAmount;
}