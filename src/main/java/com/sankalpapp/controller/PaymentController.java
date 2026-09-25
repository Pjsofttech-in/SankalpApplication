package com.sankalpapp.controller;

import com.razorpay.RazorpayException;
import com.sankalpapp.dto.request.PaymentRequest;
import com.sankalpapp.dto.response.PaymentResponse;
import com.sankalpapp.dto.response.TestSeriesPurchaseResponse;
import com.sankalpapp.dto.response.VMMaterialPurchaseResponse;
import com.sankalpapp.entity.Payment;
import com.sankalpapp.entity.TestSeriesOrder;
import com.sankalpapp.entity.VMOrder;
import com.sankalpapp.repository.PaymentRepository;
import com.sankalpapp.repository.TestSeriesOrderRepository;
import com.sankalpapp.repository.VMOrderRepository;
import com.sankalpapp.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.utils.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;
    private final VMOrderRepository vmOrderRepository;
    private final TestSeriesOrderRepository testSeriesOrderRepository;

    // Save Payment
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public PaymentResponse savePayment(@RequestBody PaymentRequest request) {

        return paymentService.savePayment(request);
    }

    // ✅ Create Order
    @PostMapping("/create-order")
//    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public String createOrder(@RequestBody PaymentRequest request) throws Exception {

        JSONObject order = paymentService.createOrder(request);

        return order.toString(); //  FIX
    }

    @PostMapping("/purchase/ebook/{ebookId}")
    public ResponseEntity<VMMaterialPurchaseResponse> purchaseMaterial(
            @PathVariable Long ebookId,
            @RequestBody PaymentRequest request) throws RazorpayException {

        VMMaterialPurchaseResponse response =
                paymentService.createEbookOrder(ebookId, request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/purchase/testSeries/{testSeriesId}")
    public ResponseEntity<TestSeriesPurchaseResponse> purchaseTestSeries(
            @PathVariable Long testSeriesId,
            @RequestBody PaymentRequest request) throws RazorpayException {

        TestSeriesPurchaseResponse response =
                paymentService.createTestSeriesOrder(testSeriesId, request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify/ebook")
    public ResponseEntity<String> purchaseMaterialVerify(
            @RequestBody PaymentRequest request) {

        String status = verifyPayment(request);
        VMOrder order = vmOrderRepository.findById(request.getOrderId()).orElseThrow();
        order.setOrderStatus(StringUtils.equals(status, "Payment Successful") ? "SUCCESS" : "FAILED");
        vmOrderRepository.save(order);

        return ResponseEntity.ok(status);
    }

    @PostMapping("/verify/testSeries")
    public ResponseEntity<String> purchaseTestSeriesVerify(
            @RequestBody PaymentRequest request) {

        String status = verifyPayment(request);
        TestSeriesOrder order = testSeriesOrderRepository.findById(request.getOrderId()).orElseThrow();
        order.setOrderStatus(StringUtils.equals(status, "Payment Successful") ? "SUCCESS" : "FAILED");
        testSeriesOrderRepository.save(order);

        return ResponseEntity.ok(status);
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