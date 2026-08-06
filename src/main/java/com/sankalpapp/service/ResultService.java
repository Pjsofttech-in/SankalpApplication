package com.sankalpapp.service;

import com.sankalpapp.dto.Request.ResultRequest;
import com.sankalpapp.dto.Response.ResultResponse;

import java.util.List;

public interface ResultService {

    ResultResponse saveResult(ResultRequest request);

    ResultResponse updateResult(Long id, ResultRequest request);

    void deleteResult(Long id);

    ResultResponse getResultById(Long id);

    List<ResultResponse> getAllResults();
}