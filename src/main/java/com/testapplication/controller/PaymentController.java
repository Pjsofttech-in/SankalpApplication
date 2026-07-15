package com.testapplication.controller;

import com.razorpay.RazorpayException;
import com.testapplication.entity.Payment;
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

    // Save Payment
    @PostMapping
    public Payment savePayment(@RequestBody Payment payment) {
        return paymentService.savePayment(payment);
    }

    // Create Razorpay Order
    @PostMapping("/create-order")
    public String createOrder(@RequestParam Double amount)
            throws RazorpayException {

        return paymentService.createOrder(amount).toString();
    }

    // Get All Payments
    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    // Get Payment By Id
    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    // Update Payment
    @PutMapping("/{id}")
    public Payment updatePayment(@PathVariable Long id,
                                 @RequestBody Payment payment) {

        return paymentService.updatePayment(id, payment);
    }

    // Delete Payment
    @DeleteMapping("/{id}")
    public String deletePayment(@PathVariable Long id) {

        paymentService.deletePayment(id);

        return "Payment Deleted Successfully";
    }
}