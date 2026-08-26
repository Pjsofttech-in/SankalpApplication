package com.sankalpapp.service;

import com.sankalpapp.dto.Request.TestSeriesExamRequest;
import com.sankalpapp.dto.Request.TestSeriesRequest;
import com.sankalpapp.dto.Response.TestSeriesProgressResponse;
import com.sankalpapp.dto.Response.TestSeriesResponse;
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