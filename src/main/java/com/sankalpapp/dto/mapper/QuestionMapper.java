package com.sankalpapp.dto.mapper;

import com.sankalpapp.dto.response.QuestionResponse;
import com.sankalpapp.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    @Mapping(source = "section.id", target = "sectionId")
    @Mapping(source = "section.name", target = "sectionName")
    QuestionResponse toResponse(Question entity);
}