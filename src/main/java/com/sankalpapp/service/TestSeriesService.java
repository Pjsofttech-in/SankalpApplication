package com.sankalpapp.service;

import com.sankalpapp.dto.request.TestSeriesExamRequest;
import com.sankalpapp.dto.request.TestSeriesRequest;
import com.sankalpapp.dto.response.TestSeriesProgressResponse;
import com.sankalpapp.dto.response.TestSeriesResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TestSeriesService {

    TestSeriesProgressResponse getStudentProgress(
            Long testSeriesId,
            Long studentId
    );

    TestSeriesResponse create(
            TestSeriesRequest request, MultipartFile image
    );

    TestSeriesResponse update(
            Long id,
            TestSeriesRequest request,
            MultipartFile image
    );

    TestSeriesResponse getById(
            Long id
    );

    List<TestSeriesResponse> getAll();

    void delete(
            Long id
    );

    TestSeriesResponse addExam(
            Long testSeriesId,
            TestSeriesExamRequest request
    );

    TestSeriesResponse removeExam(
            Long testSeriesId,
            Long examId
    );
}