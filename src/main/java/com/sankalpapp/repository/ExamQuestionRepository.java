package com.sankalpapp.repository;

import com.sankalpapp.entity.Exam;
import com.sankalpapp.entity.ExamQuestion;
import com.sankalpapp.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExamQuestionRepository
        extends JpaRepository<ExamQuestion, Long> {

    List<ExamQuestion> findByExamOrderBySequenceAsc(Exam exam);

    Optional<ExamQuestion> findByExamAndQuestion(
            Exam exam,
            Question question
    );

    Optional<ExamQuestion> findByExamIdAndQuestionId(
            Long examId,
            Long questionId
    );

    List<ExamQuestion> findByExamIdOrderBySequenceAsc(
            Long examId
    );

    boolean existsByExamIdAndQuestionId(
            Long examId,
            Long questionId
    );

    @Query("""
                SELECT COALESCE(SUM(eq.marks), 0)
                FROM ExamQuestion eq
                WHERE eq.exam.id = :examId
                  AND eq.active = true
            """)
    long sumMarksByExamId(@Param("examId") Long examId);

    long countByExamIdAndActiveTrue(
            Long examId
    );
}