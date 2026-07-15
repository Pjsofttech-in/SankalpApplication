package com.testapplication.service;

import com.testapplication.entity.StudentAnswer;

import java.util.List;

public interface StudentAnswerService {

    StudentAnswer saveAnswer(StudentAnswer answer);

    StudentAnswer updateAnswer(Long id, StudentAnswer answer);

    void deleteAnswer(Long id);

    StudentAnswer getAnswerById(Long id);

    List<StudentAnswer> getAllAnswers();
}