package com.sankalpapp.repository;

import com.sankalpapp.entity.ExamAttempt;
import com.sankalpapp.entity.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentAnswerRepository
        extends JpaRepository<StudentAnswer, Long> {

    List<StudentAnswer> findByAttempt(
            ExamAttempt attempt
    );

    Optional<StudentAnswer> findByAttemptIdAndQuestionId(
            Long attemptId,
            Long questionId
    );
}