package com.sankalpapp.controller;

import com.sankalpapp.dto.Request.PaymentRequest;
import com.sankalpapp.dto.Response.PaymentResponse;
import com.sankalpapp.entity.Payment;
import com.sankalpapp.repository.PaymentRepository;
import com.sankalpapp.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    // Save Payment
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public PaymentResponse savePayment(@RequestBody PaymentRequest request) {

        return paymentService.savePayment(request);
    }

//    // Create Razorpay Order
//    @PostMapping("/create-order")
//    public String createOrder(@RequestParam Double amount)
//            throws RazorpayException {
//
//        return paymentService.createOrder(amount).toString();
//    }

    // ✅ Create Order
    @PostMapping("/create-order")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public String createOrder(@RequestBody PaymentRequest request) throws Exception {

        JSONObject order = paymentService.createOrder(request);

        return order.toString(); //  FIX
    }
    // ✅ Verify Payment
    @PostMapping("/verify")
    public String verifyPayment(@RequestBody PaymentRequest request) {

        boolean isValid = paymentService.verifyPayment(
                request.getOrderId(),
                request.getPaymentId(),
                request.getSignature()
        );

        Payment payment = paymentRepository.findByOrderId(request.getOrderId()).orElseThrow();
        payment.setOrderId(request.getOrderId());
        payment.setPaymentId(request.getPaymentId());
        payment.setSignature(request.getSignature());
        payment.setPaymentStatus(isValid ? "SUCCESS" : "FAILED");

        paymentRepository.save(payment);

        return isValid ? "Payment Successful" : "Payment Failed";
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