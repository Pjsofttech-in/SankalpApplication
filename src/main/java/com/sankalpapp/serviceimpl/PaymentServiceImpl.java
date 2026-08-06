package com.sankalpapp.serviceimpl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.sankalpapp.dto.Request.PaymentRequest;
import com.sankalpapp.dto.Response.PaymentResponse;
import com.sankalpapp.entity.Payment;
import com.sankalpapp.entity.Student;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.repository.PaymentRepository;
import com.sankalpapp.repository.StudentRepository;
import com.sankalpapp.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final RazorpayClient razorpayClient;

    @Override
    public PaymentResponse savePayment(PaymentRequest request) {

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found with id : " + request.getStudentId()));

        Payment payment = Payment.builder()
                .amount(request.getAmount())
                .paymentMode(request.getPaymentMode())
                .transactionId(request.getTransactionId())
                .paymentStatus(request.getPaymentStatus())
                .student(student)
                .active(true)
                .build();

        return mapToResponse(paymentRepository.save(payment));
    }

    @Override
    public PaymentResponse updatePayment(Long id, PaymentRequest request) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found with id : " + id));

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found with id : " + request.getStudentId()));

        payment.setAmount(request.getAmount());
        payment.setPaymentMode(request.getPaymentMode());
        payment.setTransactionId(request.getTransactionId());
        payment.setPaymentStatus(request.getPaymentStatus());
        payment.setStudent(student);

        return mapToResponse(paymentRepository.save(payment));
    }

    @Override
    public void deletePayment(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found with id : " + id));

        paymentRepository.delete(payment);
    }

    @Override
    public PaymentResponse getPaymentById(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found with id : " + id));

        return mapToResponse(payment);
    }

    @Override
    public List<PaymentResponse> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public JSONObject createOrder(Double amount) throws RazorpayException {

        JSONObject options = new JSONObject();

        options.put("amount", amount * 100);
        options.put("currency", "INR");
        options.put("receipt", "receipt_" + System.currentTimeMillis());

        Order order = razorpayClient.orders.create(options);

        return order.toJson();
    }

    private PaymentResponse mapToResponse(Payment payment) {

        return PaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .orderId(payment.getOrderId())
                .paymentId(payment.getPaymentId())
                .transactionId(payment.getTransactionId())
                .paymentMode(payment.getPaymentMode())
                .paymentStatus(payment.getPaymentStatus())
                .paymentDate(payment.getPaymentDate())
                .active(payment.getActive())

                .studentId(payment.getStudent().getId())
                .studentName(payment.getStudent().getStudentName())

                .build();
    }
}