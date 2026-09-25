package com.sankalpapp.repository;

import com.sankalpapp.entity.TestSeriesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TestSeriesOrderRepository extends JpaRepository<TestSeriesOrder, String> {
    @Query("SELECT o FROM TestSeriesOrder o WHERE o.student.id = :userId")
    List<TestSeriesOrder> findOrdersByUserId(Long userId);

    @Query(value = """
        SELECT DATE(created_at) AS period,
               COUNT(*) AS paymentCount,
               COALESCE(SUM(amount), 0) AS totalAmount
        FROM test_series_order
        WHERE MONTH(created_at) = :month
        AND YEAR(created_at) = :year
        AND order_status = 'SUCCESS'
        GROUP BY DATE(created_at)
        ORDER BY DATE(created_at)
        """, nativeQuery = true)
    List<Object[]> getPaymentsDayWise(
            @Param("year") int year,
            @Param("month") int month
    );

    @Query(value = """
            SELECT DATE_FORMAT(created_at, '%b') AS period,
                   COUNT(*) AS paymentCount,
                   COALESCE(SUM(amount), 0) AS totalAmount
            FROM test_series_order
            WHERE YEAR(created_at) = :year
            AND order_status = 'SUCCESS'
            GROUP BY MONTH(created_at)
            ORDER BY MONTH(created_at)
            """, nativeQuery = true)
    List<Object[]> getPaymentsMonthWise(
            @Param("year") int year
    );

    @Query(value = """
            SELECT YEAR(created_at) AS period,
                   COUNT(*) AS paymentCount,
                   COALESCE(SUM(amount), 0) AS totalAmount
            FROM test_series_order
            WHERE YEAR(created_at) BETWEEN :year1 AND :year2
              AND order_status = 'SUCCESS'
            GROUP BY YEAR(created_at)
            ORDER BY YEAR(created_at)
            """, nativeQuery = true)
    List<Object[]> getPaymentsYearWise(
            @Param("year1") int year1,
            @Param("year2") int year2
    );





}