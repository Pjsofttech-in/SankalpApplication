package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebCounter;

import java.util.List;

public interface CounterService {
    WebCounter createCounter(WebCounter webCounter, String url);

    List<WebCounter> getAllByBranchCode(String url);

    WebCounter updateCounter(Long id, WebCounter webCounter, String url);

    void deleteCounter(Long id, String url);

    WebCounter getCounterById(Long id, String url);
}