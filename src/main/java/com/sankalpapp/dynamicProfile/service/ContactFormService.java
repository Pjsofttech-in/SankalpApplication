package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebContactForm;

import java.util.List;

public interface ContactFormService {
    WebContactForm create(WebContactForm webContactForm, String role, String email, String url, String branchCodeFromRequest);
    List<WebContactForm> getAllByBranchCode(String role, String email, String url, String branchCode);
    WebContactForm update(Long id, WebContactForm webContactForm, String role, String email, String url);
    void delete(Long id, String role, String email, String url);
    WebContactForm getById(Long id, String role, String email, String url);
}