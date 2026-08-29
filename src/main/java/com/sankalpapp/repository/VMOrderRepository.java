package com.sankalpapp.repository;

import com.sankalpapp.entity.VMOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VMOrderRepository extends JpaRepository<VMOrder, String> {

    @Query("SELECT o FROM VMOrder o WHERE o.vUser.id = :userId AND o.orderStatus = 'PAID'")
    List<VMOrder> findPaidOrdersByUserId(@Param("userId") Long userId);


    @Query("SELECT o FROM VMOrder o WHERE o.vUser.id = :userId")
    List<VMOrder> findOrdersByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(o), SUM(o.amount) FROM VMOrder o WHERE o.createdAt BETWEEN :startDate AND :endDate AND o.orderStatus = 'PAID'")
    List<Object[]> findCountAndRevenueByDateRange(@Param("startDate") LocalDate start, @Param("endDate") LocalDate end); // ✅

    @Query("SELECT COUNT(o), SUM(o.amount) FROM VMOrder o WHERE o.orderStatus = 'PAID'")
    List<Object[]> findTotalCountAndRevenue();


    @Query("SELECT MONTH(o.createdAt), COUNT(o), SUM(o.amount) " +
            "FROM VMOrder o " +
            "WHERE o.createdAt BETWEEN :startDate AND :endDate AND o.orderStatus = 'PAID' " +
            "GROUP BY MONTH(o.createdAt) " +
            "ORDER BY MONTH(o.createdAt)")
    List<Object[]> findMonthlyReport(@Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);


    @Query("SELECT YEAR(o.createdAt), COUNT(o), SUM(o.amount) " +
            "FROM VMOrder o " +
            "WHERE o.orderStatus = 'PAID' " +
            "GROUP BY YEAR(o.createdAt) " +
            "ORDER BY YEAR(o.createdAt)")
    List<Object[]> findYearlyReport();

    @Query("SELECT YEAR(o.createdAt), COUNT(o), SUM(o.amount) " +
            "FROM VMOrder o " +
            "WHERE YEAR(o.createdAt) IN (:year1, :year2) AND o.orderStatus = 'PAID' " +
            "GROUP BY YEAR(o.createdAt) " +
            "ORDER BY YEAR(o.createdAt)")
    List<Object[]> findComparisonData(@Param("year1") int year1, @Param("year2") int year2);

    @Query("SELECT o.vmMaterial.categoryName, SUM(o.amount) " +
            "FROM VMOrder o " +
            "WHERE o.orderStatus = 'PAID' AND o.vmMaterial.categoryName = :categoryName " +
            "GROUP BY o.vmMaterial.categoryName")
    List<Object[]> findCategoryRevenueByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT m.categoryName, SUM(o.amount) " +
            "FROM VMOrder o JOIN o.vmMaterial m " +
            "WHERE o.orderStatus = 'PAID' AND m.categoryName IS NOT NULL " +
            "GROUP BY m.categoryName")
    List<Object[]> getAllCategoryRevenue();

}