package com.sankalpapp.repository;

import com.sankalpapp.entity.Exam;
import com.sankalpapp.entity.Result;
import com.sankalpapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResultRepository
        extends JpaRepository<Result, Long> {

    Optional<Result> findByAttemptId(Long attemptId);

    List<Result> findByExamIdAndActiveTrue(Long examId);

    List<Result> findByExamIdAndPublishedTrueAndActiveTrue(
            Long examId
    );
}