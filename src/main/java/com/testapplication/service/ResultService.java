package com.testapplication.service;

import com.testapplication.entity.Result;

import java.util.List;

public interface ResultService {

    Result saveResult(Result result);

    Result updateResult(Long id, Result result);

    void deleteResult(Long id);

    Result getResultById(Long id);

    List<Result> getAllResults();
}