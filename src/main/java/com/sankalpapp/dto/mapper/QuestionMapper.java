package com.sankalpapp.dto.mapper;

import com.sankalpapp.dto.Response.QuestionResponse;
import com.sankalpapp.entity.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionResponse toResponse(Question entity);
}