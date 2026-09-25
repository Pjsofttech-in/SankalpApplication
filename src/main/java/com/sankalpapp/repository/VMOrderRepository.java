package com.sankalpapp.repository;

import com.sankalpapp.entity.VMOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VMOrderRepository extends JpaRepository<VMOrder, String> {

    @Query(value = """
        SELECT DATE(created_at) AS period,
               COUNT(*) AS paymentCount,
               COALESCE(SUM(amount), 0) AS totalAmount
        FROM vmorder
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
        FROM vmorder
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
            FROM vmorder
            WHERE YEAR(created_at) BETWEEN :year1 AND :year2
              AND order_status = 'SUCCESS'
            GROUP BY YEAR(created_at)
            ORDER BY YEAR(created_at)
            """, nativeQuery = true)
    List<Object[]> getPaymentsYearWise(
            @Param("year1") int year1,
            @Param("year2") int year2
    );

    @Query("SELECT YEAR(o.createdAt), COUNT(o), SUM(o.amount) " +
            "FROM VMOrder o " +
            "WHERE YEAR(o.createdAt) IN (:year1, :year2) AND o.orderStatus = 'SUCCESS' " +
            "GROUP BY YEAR(o.createdAt) " +
            "ORDER BY YEAR(o.createdAt)")
    List<Object[]> findComparisonData(@Param("year1") int year1, @Param("year2") int year2);

    @Query("SELECT o.vmMaterial.categoryName, SUM(o.amount) " +
            "FROM VMOrder o " +
            "WHERE o.orderStatus = 'SUCCESS' AND o.vmMaterial.categoryName = :categoryName " +
            "GROUP BY o.vmMaterial.categoryName")
    List<Object[]> findCategoryRevenueByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT m.categoryName, SUM(o.amount) " +
            "FROM VMOrder o JOIN o.vmMaterial m " +
            "WHERE o.orderStatus = 'SUCCESS' AND m.categoryName IS NOT NULL " +
            "GROUP BY m.categoryName")
    List<Object[]> getAllCategoryRevenue();

}