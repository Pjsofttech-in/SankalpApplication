package com.sankalpapp.repository;

import com.sankalpapp.entity.VMMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VMMaterialRepository extends JpaRepository<VMMaterial, Long> {
    @Query("SELECT COALESCE(COUNT(v), 0), COALESCE(SUM(v.price), 0.0) " +
            "FROM VMMaterial v WHERE v.createdDate BETWEEN :startDate AND :endDate")
    List<Object[]> findCountAndRevenueByDateRange(LocalDate startDate, LocalDate endDate);

    @Query("SELECT COALESCE(COUNT(v), 0), COALESCE(SUM(v.price), 0.0) FROM VMMaterial v")
    List<Object[]> findTotalCountAndRevenue();

    @Query("SELECT FUNCTION('MONTH', v.createdDate) AS month, COUNT(v) AS totalOrders, SUM(v.price) AS totalRevenue " +
            "FROM VMMaterial v WHERE v.createdDate BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('MONTH', v.createdDate) ORDER BY month")
    List<Object[]> findMonthlyReport(LocalDate startDate, LocalDate endDate);

    @Query("SELECT FUNCTION('YEAR', v.createdDate) AS year, COUNT(v) AS totalOrders, SUM(v.price) AS totalRevenue " +
            "FROM VMMaterial v GROUP BY FUNCTION('YEAR', v.createdDate) ORDER BY year")
    List<Object[]> findYearlyReport();

    @Query("SELECT FUNCTION('YEAR', v.createdDate) AS year, COUNT(v.id) AS totalOrders, SUM(v.price) AS totalRevenue " +
            "FROM VMMaterial v WHERE FUNCTION('YEAR', v.createdDate) IN (:year1, :year2) " +
            "GROUP BY FUNCTION('YEAR', v.createdDate)")
    List<Object[]> findComparisonData(int year1, int year2);

    @Query("SELECT v.categoryName, SUM(v.price) AS totalRevenue " +
            "FROM VMMaterial v WHERE v.categoryName = :categoryName " +
            "GROUP BY v.categoryName")
    List<Object[]> findCategoryRevenueByCategoryName(String categoryName);

    @Query("SELECT v.categoryName, SUM(v.price) " +
            "FROM VMMaterial v WHERE v.status = 'paid' " +
            "GROUP BY v.categoryName")
    List<Object[]> getAllCategoryRevenue();

    @Query("SELECT DATE(vm.createdDate), COUNT(vm), SUM(vm.price) " +
            "FROM VMMaterial vm " +
            "WHERE YEAR(vm.createdDate) = :year AND MONTH(vm.createdDate) = :month " +
            "GROUP BY DATE(vm.createdDate) " +
            "ORDER BY DATE(vm.createdDate)")
    List<Object[]> findDailyCountAndRevenue(int year, int month);

    Optional<VMMaterial> findBySlug(String slug);

    boolean existsBySlug(String slug);
}
