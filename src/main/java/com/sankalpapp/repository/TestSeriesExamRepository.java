package com.sankalpapp.repository;

import com.sankalpapp.entity.TestSeriesExam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestSeriesExamRepository
        extends JpaRepository<TestSeriesExam, Long> {

    List<TestSeriesExam> findByTestSeriesIdAndActiveTrueOrderBySequenceAsc(
            Long testSeriesId
    );

    List<TestSeriesExam> findByTestSeriesIdOrderBySequenceAsc(
            Long testSeriesId
    );

    Optional<TestSeriesExam> findByTestSeriesIdAndExamId(
            Long testSeriesId,
            Long examId
    );

    boolean existsByTestSeriesIdAndExamId(
            Long testSeriesId,
            Long examId
    );

    void deleteByTestSeriesIdAndExamId(
            Long testSeriesId,
            Long examId
    );
}