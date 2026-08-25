package com.sankalpapp.repository;

import com.sankalpapp.entity.TestSeries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestSeriesRepository
        extends JpaRepository<TestSeries, Long> {
}