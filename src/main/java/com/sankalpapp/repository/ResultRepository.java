package com.sankalpapp.repository;

import com.sankalpapp.entity.Result;
import com.sankalpapp.entity.Student;
import com.sankalpapp.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResultRepository
        extends JpaRepository<Result, Long> {

    Optional<Result> findByAttemptId(Long attemptId);

    List<Result> findByStudent(Student student);

    List<Result> findByExam(Exam exam);


    Optional<Result> findByStudentIdAndExamId(
            Long studentId,
            Long examId
    );
}