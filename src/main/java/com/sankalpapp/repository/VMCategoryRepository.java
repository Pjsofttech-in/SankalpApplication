package com.sankalpapp.repository;

import com.sankalpapp.entity.VMCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface VMCategoryRepository extends JpaRepository<VMCategory, Long> {

    @Query("SELECT v FROM VMCategory v WHERE LOWER(v.categoryName) = LOWER(:categoryName)")
    Optional<VMCategory> findCategoryByName(@Param("categoryName") String categoryName);

    List<VMCategory> findByCreatedDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT v FROM VMCategory v WHERE v.categoryName = :categoryName")
    Optional<VMCategory> findByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT v FROM VMCategory v LEFT JOIN FETCH v.vmMaterialType")
    List<VMCategory> findAllWithMaterialType();

    @Query("SELECT v FROM VMCategory v WHERE v.vmMaterialType.materialtype = :materialTypeName")
    List<VMCategory> findByMaterialTypeName(@Param("materialTypeName") String materialTypeName);


}
