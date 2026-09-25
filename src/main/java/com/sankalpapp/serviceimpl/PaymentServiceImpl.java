package com.sankalpapp.serviceimpl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.sankalpapp.dto.request.PaymentRequest;
import com.sankalpapp.dto.response.PaymentResponse;
import com.sankalpapp.dto.response.TestSeriesPurchaseResponse;
import com.sankalpapp.dto.response.VMMaterialPurchaseResponse;
import com.sankalpapp.entity.*;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.repository.*;
import com.sankalpapp.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private final PaymentRepository paymentRepository;

    @Autowired
    private final StudentRepository studentRepository;

    @Autowired
    private final VMOrderRepository vmOrderRepository;

    @Autowired
    private final VMMaterialRepository vmMaterialRepository;

    @Autowired
    private final TestSeriesRepository testSeriesRepository;

    @Autowired
    private final TestSeriesOrderRepository testSeriesOrderRepository;

    @Value("${razorpay.key}")
    private String key;

    @Value("${razorpay.secret}")
    private String secret;

    @Override
    public PaymentResponse savePayment(PaymentRequest request) {
        Student student = null;
        if (Objects.nonNull(request.getStudentId()) &&
                Payment.PaymentStatus.SUCCESS.name().equalsIgnoreCase(request.getPaymentStatus())) {
            student = studentRepository.findById(request.getStudentId()).orElse(null);
        }

        Payment payment = Payment.builder()
                .amount(request.getAmount())
                .mobile(request.getMobileNo())
                .paymentMode(request.getPaymentMode())
                .orderId(request.getOrderId())
                .paymentId(request.getPaymentId())
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
    public JSONObject createOrder(PaymentRequest request) throws RazorpayException {

        RazorpayClient razorpayClient = new RazorpayClient(key, secret);

        JSONObject options = new JSONObject();

        String transactionId = "receipt_" + System.currentTimeMillis();

        options.put("amount", request.getAmount() * 100);
        options.put("currency", "INR");
        options.put("receipt", transactionId);

        Order order = razorpayClient.orders.create(options);
        request.setTransactionId(transactionId);
        request.setOrderId(order.get("id"));
        savePayment(request);

        return order.toJson();
    }

    @Override
    public boolean verifyPayment(String orderId, String paymentId, String signature) {
        try {
            String data = orderId + "|" + paymentId;

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(), "HmacSHA256"));

            byte[] hash = mac.doFinal(data.getBytes());

            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }

            return hex.toString().equals(signature);

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public VMMaterialPurchaseResponse createEbookOrder(Long ebookId, PaymentRequest request) throws RazorpayException {
        JSONObject paymentOrder = createOrder(request);

        Student student = studentRepository.findById(request.getStudentId()).orElseThrow();

        VMMaterial material = vmMaterialRepository
                .findById(ebookId)
                .orElseThrow(() ->
                        new RuntimeException("Ebook not found"));

        VMOrder order = VMOrder.builder()
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .orderStatus("CREATED")
                .customerEmail(student.getEmail())
                .customerPhone(student.getMobile())
                .student(student)
                .vmMaterial(material)
                .createdAt(LocalDateTime.now())
                .build();

        vmOrderRepository.save(order);

        return VMMaterialPurchaseResponse.builder()
                .orderId(request.getOrderId())
                .materialId(material.getId())
                .materialName(material.getChapterName())
                .amount(request.getAmount())
                .orderStatus("CREATED")
                .paymentStatus("PENDING")
                .build();

    }

    @Override
    public TestSeriesPurchaseResponse createTestSeriesOrder(Long testSeriesId, PaymentRequest request) throws RazorpayException {
        JSONObject paymentOrder = createOrder(request);

        Student student = studentRepository.findById(request.getStudentId()).orElseThrow();

        TestSeries material = testSeriesRepository
                .findById(testSeriesId)
                .orElseThrow(() ->
                        new RuntimeException("TestSeries not found"));

        TestSeriesOrder order = TestSeriesOrder.builder()
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .orderStatus("CREATED")
                .customerEmail(student.getEmail())
                .customerPhone(student.getMobile())
                .student(student)
                .testSeries(material)
                .createdAt(LocalDateTime.now())
                .build();

        testSeriesOrderRepository.save(order);

        return TestSeriesPurchaseResponse.builder()
                .orderId(request.getOrderId())
                .testSeriesId(material.getId())
                .testSeriesName(material.getTitle())
                .amount(request.getAmount())
                .orderStatus("CREATED")
                .paymentStatus("PENDING")
                .build();

    }

    private PaymentResponse mapToResponse(Payment payment) {
        PaymentResponse response = PaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .orderId(payment.getOrderId())
                .paymentId(payment.getPaymentId())
                .transactionId(payment.getTransactionId())
                .paymentMode(payment.getPaymentMode())
                .paymentStatus(payment.getPaymentStatus())
                .paymentDate(payment.getPaymentDate())
                .active(payment.getActive())
                .build();

        if (Objects.nonNull(payment.getStudent())) {
            response.setStudentId(payment.getStudent().getId());
            response.setStudentName(payment.getStudent().getStudentName());
        }

        return response;
    }
}