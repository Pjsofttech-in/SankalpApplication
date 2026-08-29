package com.sankalpapp.repository;

import com.sankalpapp.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Fetch payment by orderId
    Optional<Payment> findByOrderId(String orderId);

    Optional<Payment> findByMobileAndPaymentStatusIgnoreCase(String mobile, String paymentStatus);

}