package com.sankalpapp.service;

import com.sankalpapp.dto.Response.ExamQuestionDto;
import com.sankalpapp.entity.ExamQuestion;

import java.util.List;

public interface ExamQuestionService {

    ExamQuestionDto addQuestion(
            Long examId,
            Long questionId,
            Integer sequence,
            Integer marks
    );

    List<ExamQuestionDto> getQuestionsByExam(
            Long examId
    );

    void removeQuestion(
            Long examId,
            Long questionId
    );

    ExamQuestionDto updateSequence(
            Long examId,
            Long questionId,
            Integer sequence
    );

    ExamQuestionDto updateMarks(
            Long examId,
            Long questionId,
            Integer marks
    );
}