package com.testapplication.serviceimpl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.testapplication.entity.Payment;
import com.testapplication.exception.ResourceNotFoundException;
import com.testapplication.repository.PaymentRepository;
import com.testapplication.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RazorpayClient razorpayClient;

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(Long id, Payment payment) {

        Payment existing = getPaymentById(id);

        existing.setAmount(payment.getAmount());
        existing.setPaymentMode(payment.getPaymentMode());
        existing.setTransactionId(payment.getTransactionId());
        existing.setPaymentStatus(payment.getPaymentStatus());
        existing.setPaymentDate(payment.getPaymentDate());
        existing.setStudent(payment.getStudent());

        return paymentRepository.save(existing);
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.delete(getPaymentById(id));
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found with id : " + id));
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Razorpay Order Creation
    @Override
    public JSONObject createOrder(Double amount) throws RazorpayException {

        JSONObject options = new JSONObject();

        options.put("amount", amount * 100); // Paisa
        options.put("currency", "INR");
        options.put("receipt", "receipt_" + System.currentTimeMillis());

        Order order = razorpayClient.orders.create(options);

        return order.toJson();
    }
}