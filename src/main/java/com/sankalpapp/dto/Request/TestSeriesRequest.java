package com.sankalpapp.dto.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestSeriesRequest {

    private String title;

    private String description;

    private Boolean active;
}