package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebHRDetails;

import java.util.List;

public interface WebHRDetailsService {
    WebHRDetails create(WebHRDetails webHRDetails, String role, String email, String url);
    List<WebHRDetails> getAllByBranchCode(String role, String email, String url, String branchCode);
    WebHRDetails getById(Long id, String role, String email, String url);
    WebHRDetails update(Long id, WebHRDetails webHRDetails, String role, String email, String url);
    void delete(Long id, String role, String email, String url);
}
