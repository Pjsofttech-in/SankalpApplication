package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebFooter;

import java.util.List;

public interface FooterService {
    WebFooter createFooter(WebFooter webFooter, String role, String email, String url);
    List<WebFooter> getAllFootersByBranchCode(String role, String email, String url, String branchCode);
    WebFooter updateFooter(Long id, WebFooter webFooter, String role, String email, String url);
    void deleteFooter(Long id, String role, String email, String url);
    WebFooter getFooterById(Long id, String role, String email, String url);
}
