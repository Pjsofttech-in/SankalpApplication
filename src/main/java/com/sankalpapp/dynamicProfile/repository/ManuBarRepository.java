package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.WebManuBar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManuBarRepository extends JpaRepository<WebManuBar, Long> {

    @Query("SELECT m FROM WebManuBar m WHERE m.branchCode = :branchCode ORDER BY m.id DESC")
    List<WebManuBar> findAllByBranchCode(String branchCode);

    Optional<WebManuBar> findFirstByBranchCode(String branchCode);

}