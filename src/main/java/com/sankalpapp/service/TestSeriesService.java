package com.sankalpapp.service;

import com.sankalpapp.dto.Request.AddExamToTestSeriesRequest;
import com.sankalpapp.dto.Request.CreateTestSeriesRequest;
import com.sankalpapp.dto.Request.ReorderExamRequest;
import com.sankalpapp.entity.TestSeries;

import java.util.List;

public interface TestSeriesService {

    TestSeries create(CreateTestSeriesRequest request);

    TestSeries getById(Long id);

    List<TestSeries> getAll();

    TestSeries update(
            Long id,
            CreateTestSeriesRequest request
    );

    void delete(Long id);

    void addExam(
            Long testSeriesId,
            AddExamToTestSeriesRequest request
    );

    void removeExam(
            Long testSeriesId,
            Long examId
    );

    void reorderExam(
            Long testSeriesId,
            Long examId,
            ReorderExamRequest request
    );
}