package com.sankalpapp.repository;

import com.sankalpapp.entity.Exam;
import com.sankalpapp.entity.TestSeries;
import com.sankalpapp.entity.TestSeriesExam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestSeriesExamRepository
        extends JpaRepository<TestSeriesExam, Long> {

    List<TestSeriesExam> findByTestSeriesOrderBySequenceAsc(
            TestSeries testSeries
    );

    Optional<TestSeriesExam> findByTestSeriesAndExam(
            TestSeries testSeries,
            Exam exam
    );

    void deleteByTestSeriesAndExam(
            TestSeries testSeries,
            Exam exam
    );
}