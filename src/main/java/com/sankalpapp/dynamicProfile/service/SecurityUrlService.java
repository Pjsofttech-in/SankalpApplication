package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;

import java.util.List;

public interface SecurityUrlService {
    WebSecurityUrl create(WebSecurityUrl webSecurityUrl); // No permission

    List<WebSecurityUrl> getAll();

    WebSecurityUrl update(long id, WebSecurityUrl webSecurityUrl);
}