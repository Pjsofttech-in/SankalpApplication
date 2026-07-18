package com.testapplication.controller;

import com.razorpay.RazorpayException;
import com.testapplication.dto.Request.PaymentRequest;
import com.testapplication.dto.Response.PaymentResponse;
import com.testapplication.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentResponse savePayment(@RequestBody PaymentRequest request) {

        return paymentService.savePayment(request);
    }

    @PostMapping("/create-order")
    public String createOrder(@RequestParam Double amount)
            throws RazorpayException {

        return paymentService.createOrder(amount).toString();
    }

    @GetMapping
    public List<PaymentResponse> getAllPayments() {

        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public PaymentResponse getPaymentById(@PathVariable Long id) {

        return paymentService.getPaymentById(id);
    }

    @PutMapping("/{id}")
    public PaymentResponse updatePayment(@PathVariable Long id,
                                         @RequestBody PaymentRequest request) {

        return paymentService.updatePayment(id, request);
    }

    @DeleteMapping("/{id}")
    public String deletePayment(@PathVariable Long id) {

        paymentService.deletePayment(id);

        return "Payment Deleted Successfully";
    }
}