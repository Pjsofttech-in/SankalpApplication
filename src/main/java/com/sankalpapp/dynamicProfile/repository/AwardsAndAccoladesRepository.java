package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.WebAwardsAndAccolades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AwardsAndAccoladesRepository extends JpaRepository<WebAwardsAndAccolades, Long> {

    @Query("SELECT a FROM WebAwardsAndAccolades a WHERE a.branchCode = :branchCode ORDER BY a.id DESC")
    List<WebAwardsAndAccolades> findAllByBranchCode(String branchCode);
}