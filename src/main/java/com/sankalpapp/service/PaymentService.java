package com.sankalpapp.service;

import com.razorpay.RazorpayException;
import com.sankalpapp.dto.request.PaymentRequest;
import com.sankalpapp.dto.response.PaymentResponse;
import org.json.JSONObject;

import java.util.List;

public interface PaymentService {

    PaymentResponse savePayment(PaymentRequest request);

    PaymentResponse updatePayment(Long id, PaymentRequest request);

    void deletePayment(Long id);

    PaymentResponse getPaymentById(Long id);

    List<PaymentResponse> getAllPayments();

    JSONObject createOrder(PaymentRequest request) throws RazorpayException;

    boolean verifyPayment(String orderId, String paymentId, String signature);
}