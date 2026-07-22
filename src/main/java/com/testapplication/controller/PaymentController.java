package com.testapplication.controller;

import com.razorpay.RazorpayException;
import com.testapplication.dto.Request.PaymentRequest;
import com.testapplication.dto.Response.PaymentResponse;
import com.testapplication.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    // Save Payment
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public PaymentResponse savePayment(@RequestBody PaymentRequest request) {

        return paymentService.savePayment(request);
    }

    // Create Razorpay Order
    @PostMapping("/create-order")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public String createOrder(@RequestParam Double amount)
            throws RazorpayException {

        return paymentService.createOrder(amount).toString();
    }

    // Get All Payments
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public List<PaymentResponse> getAllPayments() {

        return paymentService.getAllPayments();
    }

    // Get Payment By Id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public PaymentResponse getPaymentById(@PathVariable Long id) {

        return paymentService.getPaymentById(id);
    }

    // Update Payment
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public PaymentResponse updatePayment(@PathVariable Long id,
                                         @RequestBody PaymentRequest request) {

        return paymentService.updatePayment(id, request);
    }

    // Delete Payment
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deletePayment(@PathVariable Long id) {

        paymentService.deletePayment(id);

        return "Payment Deleted Successfully";
    }
}