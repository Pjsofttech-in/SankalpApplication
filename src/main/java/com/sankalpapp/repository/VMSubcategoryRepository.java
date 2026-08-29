package com.sankalpapp.repository;

import com.sankalpapp.entity.VMSubcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VMSubcategoryRepository extends JpaRepository<VMSubcategory, Long> {

    @Query("SELECT v FROM VMSubcategory v WHERE LOWER(v.subcategoryName) = LOWER(:subcategoryName)")
    Optional<VMSubcategory> findByName(@Param("subcategoryName") String subcategoryName);

    @Query("SELECT v FROM VMSubcategory v WHERE v.vmCategory.categoryName = :categoryName")
    List<VMSubcategory> findByCategoryName(@Param("categoryName") String categoryName);

    List<VMSubcategory> findByVmCategoryCategoryName(String categoryName);

    @Query("SELECT v FROM VMSubcategory v WHERE v.subcategoryName = :subcategoryName")
    Optional<VMSubcategory> findBySubcategoryName(@Param("subcategoryName") String subcategoryName);

    List<VMSubcategory> findByVmCategoryId(Long categoryId);

}
