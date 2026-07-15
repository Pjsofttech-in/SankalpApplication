package com.testapplication.service;

import com.testapplication.entity.AnswerKey;

import java.util.List;

public interface AnswerKeyService {

    AnswerKey saveAnswerKey(AnswerKey answerKey);

    AnswerKey updateAnswerKey(Long id, AnswerKey answerKey);

    void deleteAnswerKey(Long id);

    AnswerKey getAnswerKeyById(Long id);

    List<AnswerKey> getAllAnswerKeys();
}