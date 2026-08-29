package com.sankalpapp.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VMOrderDTO {
    private String orderId;
    private Double amount;
    private String orderStatus; // ✅ renamed
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private LocalDate createdAt;
    private VMMaterialOrderViewDTO vmMaterial;
}