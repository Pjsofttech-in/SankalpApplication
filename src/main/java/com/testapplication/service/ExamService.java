package com.testapplication.service;

import com.testapplication.entity.Exam;

import java.util.List;

public interface ExamService {

    Exam saveExam(Exam exam);

    Exam updateExam(Long id, Exam exam);

    void deleteExam(Long id);

    Exam getExamById(Long id);

    List<Exam> getAllExams();
}