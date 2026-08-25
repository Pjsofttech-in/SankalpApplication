package com.sankalpapp.service;

import com.sankalpapp.entity.Question;

import java.util.List;

public interface QuestionService {

    Question create(Question question);

    Question getById(Long id);

    List<Question> getAll();

    Question update(Long id, Question question);

    void delete(Long id);
}