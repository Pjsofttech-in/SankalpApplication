package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebContactForm;

import java.util.List;

public interface ContactFormService {
    WebContactForm create(WebContactForm webContactForm, String urlFromRequest);

    List<WebContactForm> getAllByBranchCode(String url);

    WebContactForm update(Long id, WebContactForm webContactForm, String url);

    void delete(Long id, String url);

    WebContactForm getById(Long id, String url);
}