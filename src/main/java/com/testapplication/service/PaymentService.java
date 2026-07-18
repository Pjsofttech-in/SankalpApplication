package com.testapplication.service;

import com.razorpay.RazorpayException;
import com.testapplication.dto.Request.PaymentRequest;
import com.testapplication.dto.Response.PaymentResponse;
import org.json.JSONObject;

import java.util.List;

public interface PaymentService {

    PaymentResponse savePayment(PaymentRequest request);

    PaymentResponse updatePayment(Long id, PaymentRequest request);

    void deletePayment(Long id);

    PaymentResponse getPaymentById(Long id);

    List<PaymentResponse> getAllPayments();

    JSONObject createOrder(Double amount) throws RazorpayException;
}