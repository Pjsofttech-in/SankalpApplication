package com.sankalpapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VMOrder {

    @Id
    private String orderId; // Unique ID from your side

    private Double amount;
    private String orderStatus; // ✅ renamed from 'status' to 'orderStatus'
    private String customerEmail;
    private String customerPhone;
    private LocalDate createdAt = LocalDate.now();

    @ManyToOne
    private User vUser;

    @ManyToOne
    private VMMaterial vmMaterial; // Material user is buying


}