package com.sankalpapp.repository;

import com.sankalpapp.entity.Exam;
import com.sankalpapp.entity.ExamAttempt;
import com.sankalpapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamAttemptRepository
        extends JpaRepository<ExamAttempt, Long> {

    Optional<ExamAttempt> findByStudentIdAndExamIdAndStatus(
            Long studentId,
            Long examId,
            ExamAttempt.AttemptStatus status
    );

    List<ExamAttempt> findByStudent(Student student);

    List<ExamAttempt> findByExam(Exam exam);
}