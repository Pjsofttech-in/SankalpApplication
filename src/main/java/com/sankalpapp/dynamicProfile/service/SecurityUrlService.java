package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;

import java.util.List;

public interface SecurityUrlService {
    WebSecurityUrl create(WebSecurityUrl webSecurityUrl, String role, String email); // No permission
    List<WebSecurityUrl> getAllByBranchCode(String role, String email, String branchCode);
    WebSecurityUrl update(long id, WebSecurityUrl webSecurityUrl, String role, String email);
    String getBranchCodeByUrl(String url);

}