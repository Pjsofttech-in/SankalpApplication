package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebCounter;

import java.util.List;

public interface CounterService {
    WebCounter createCounter(WebCounter webCounter, String role, String email, String url);
    List<WebCounter> getAllByBranchCode(String role, String email, String url, String branchCode);
    WebCounter updateCounter(Long id, WebCounter webCounter, String role, String email, String url);
    void deleteCounter(Long id, String role, String email, String url);
    WebCounter getCounterById(Long id, String role, String email, String url);
}