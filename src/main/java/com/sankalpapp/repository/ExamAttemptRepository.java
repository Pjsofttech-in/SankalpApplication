package com.sankalpapp.repository;

import com.sankalpapp.entity.Exam;
import com.sankalpapp.entity.ExamAttempt;
import com.sankalpapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamAttemptRepository
        extends JpaRepository<ExamAttempt, Long> {

    long countByStudentIdAndExamIdAndTestSeriesId(
            Long studentId,
            Long examId,
            Long testSeriesId
    );

    long countByStudentIdAndExamIdAndTestSeriesIsNull(
            Long studentId,
            Long examId
    );

    List<ExamAttempt> findByStudentIdAndExamIdAndTestSeriesIdOrderByAttemptNumberAsc(
            Long studentId,
            Long examId,
            Long testSeriesId
    );

    Optional<ExamAttempt> findTopByStudentIdAndExamIdAndTestSeriesIdAndStatus(
            Long studentId,
            Long examId,
            Long testSeriesId,
            ExamAttempt.AttemptStatus status
    );

    Optional<ExamAttempt> findTopByStudentIdAndExamIdOrderByIdDesc(
            Long studentId,
            Long examId
    );

    Optional<ExamAttempt> findTopByStudentIdAndExamIdAndTestSeriesIsNullAndStatus(
            Long studentId,
            Long examId,
            ExamAttempt.AttemptStatus status
    );

    List<ExamAttempt> findByStudent(Student student);

    List<ExamAttempt> findByExam(Exam exam);
}