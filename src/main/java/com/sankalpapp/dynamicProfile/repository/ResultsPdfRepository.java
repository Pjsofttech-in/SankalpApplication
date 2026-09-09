package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.ResultsPdf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultsPdfRepository extends JpaRepository<ResultsPdf, Long> {
}