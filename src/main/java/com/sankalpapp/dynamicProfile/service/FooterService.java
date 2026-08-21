package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebFooter;

import java.util.List;

public interface FooterService {
    WebFooter createFooter(WebFooter webFooter, String url);
    List<WebFooter> getAllFootersByBranchCode(String url);
    WebFooter updateFooter(Long id, WebFooter webFooter, String url);
    void deleteFooter(Long id, String url);
    WebFooter getFooterById(Long id, String url);
}
