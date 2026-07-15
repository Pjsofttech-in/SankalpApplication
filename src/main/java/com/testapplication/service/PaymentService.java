package com.testapplication.service;

import com.razorpay.RazorpayException;
import com.testapplication.entity.Payment;
import org.json.JSONObject;

import java.util.List;

public interface PaymentService {

    Payment savePayment(Payment payment);

    Payment updatePayment(Long id, Payment payment);

    void deletePayment(Long id);

    Payment getPaymentById(Long id);

    List<Payment> getAllPayments();

    // Razorpay
    JSONObject createOrder(Double amount) throws RazorpayException;
}