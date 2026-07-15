package com.testapplication.service;

import com.testapplication.entity.Question;

import java.util.List;

public interface QuestionService {

    Question saveQuestion(Question question);

    Question updateQuestion(Long id, Question question);

    void deleteQuestion(Long id);

    Question getQuestionById(Long id);

    List<Question> getAllQuestions();
}