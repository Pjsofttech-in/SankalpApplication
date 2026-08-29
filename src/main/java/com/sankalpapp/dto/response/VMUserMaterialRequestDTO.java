package com.sankalpapp.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class VMUserMaterialRequestDTO {
    private String username;
    private String email;
    private Long materialId;
    private double paidAmount;

}
