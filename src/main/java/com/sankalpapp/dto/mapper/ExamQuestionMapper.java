package com.sankalpapp.dto.mapper;

import com.sankalpapp.dto.response.ExamQuestionDto;
import com.sankalpapp.entity.ExamQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExamQuestionMapper {

    @Mapping(source = "exam.id", target = "examId")
    @Mapping(source = "question.id", target = "questionId")
    ExamQuestionDto toDto(ExamQuestion entity);

    @Mapping(source = "examId", target = "exam.id")
    @Mapping(source = "questionId", target = "question.id")
    ExamQuestion toEntity(ExamQuestionDto dto);
}