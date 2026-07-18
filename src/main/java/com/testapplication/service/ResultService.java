package com.testapplication.service;

import com.testapplication.dto.Request.ResultRequest;
import com.testapplication.dto.Response.ResultResponse;

import java.util.List;

public interface ResultService {

    ResultResponse saveResult(ResultRequest request);

    ResultResponse updateResult(Long id, ResultRequest request);

    void deleteResult(Long id);

    ResultResponse getResultById(Long id);

    List<ResultResponse> getAllResults();
}