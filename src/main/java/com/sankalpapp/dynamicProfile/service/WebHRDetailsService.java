package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebHRDetails;

import java.util.List;

public interface WebHRDetailsService {
    WebHRDetails create(WebHRDetails webHRDetails, String url);

    List<WebHRDetails> getAllByBranchCode(String url);

    WebHRDetails getById(Long id, String url);

    WebHRDetails update(Long id, WebHRDetails webHRDetails, String url);

    void delete(Long id, String url);
}
