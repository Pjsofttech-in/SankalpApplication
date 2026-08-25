package com.sankalpapp.repository;

import com.sankalpapp.entity.TestSeries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestSeriesRepository
        extends JpaRepository<TestSeries, Long> {

    List<TestSeries> findByActiveTrue();
}