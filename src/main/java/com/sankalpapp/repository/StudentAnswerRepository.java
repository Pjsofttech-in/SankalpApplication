package com.sankalpapp.repository;

import com.sankalpapp.entity.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {

    List<StudentAnswer> findByStudentId(Long studentId);

    List<StudentAnswer> findByQuestionExamId(Long examId);

}