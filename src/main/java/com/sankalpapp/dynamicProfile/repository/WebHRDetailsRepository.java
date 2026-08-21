package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.WebHRDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebHRDetailsRepository extends JpaRepository<WebHRDetails, Long> {

    @Query("SELECT w FROM WebHRDetails w WHERE w.webSecurityUrl.branchCode = :branchCode ORDER BY w.id DESC")
    List<WebHRDetails> findAllByBranchCode(String branchCode);
}