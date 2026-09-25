package com.sankalpapp.repository;

import com.sankalpapp.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Fetch payment by orderId
    Optional<Payment> findByOrderId(String orderId);

    @Query(value = """
        SELECT DATE(payment_date) AS period,
               COUNT(*) AS paymentCount,
               COALESCE(SUM(amount), 0) AS totalAmount
        FROM payments
        WHERE MONTH(created_at) = :month
          AND YEAR(created_at) = :year
          AND payment_status = 'SUCCESS'
        GROUP BY DATE(payment_date)
        ORDER BY DATE(payment_date)
        """, nativeQuery = true)
    List<Object[]> getPaymentsDayWise(
            @Param("year") int year,
            @Param("month") int month
    );

    @Query(value = """
        SELECT DATE_FORMAT(payment_date, '%b') AS period,
               COUNT(*) AS paymentCount,
               COALESCE(SUM(amount), 0) AS totalAmount
        FROM payments
        WHERE YEAR(created_at) = :year
          AND payment_status = 'SUCCESS'
        GROUP BY MONTH(payment_date)
        ORDER BY MONTH(payment_date)
        """, nativeQuery = true)
    List<Object[]> getPaymentsMonthWise(
            @Param("year") int year
    );

    @Query(value = """
            SELECT YEAR(payment_date) AS period,
                   COUNT(*) AS paymentCount,
                   COALESCE(SUM(amount), 0) AS totalAmount
            FROM payments
            WHERE YEAR(created_at) BETWEEN :year1 AND :year2
              AND payment_status = 'SUCCESS'
            GROUP BY YEAR(payment_date)
            ORDER BY YEAR(payment_date)
            """, nativeQuery = true)
    List<Object[]> getPaymentsYearWise(
            @Param("year1") int year1,
            @Param("year2") int year2
    );
}