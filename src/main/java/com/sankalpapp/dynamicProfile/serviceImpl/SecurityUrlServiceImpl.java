package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.dynamicProfile.repository.SecurityUrlrepository;
import com.sankalpapp.dynamicProfile.service.SecurityUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityUrlServiceImpl implements SecurityUrlService {

    @Autowired
    private SecurityUrlrepository repository;

    @Override
    public WebSecurityUrl create(WebSecurityUrl webSecurityUrl) {
        return repository.save(webSecurityUrl);
    }


    @Override
    public List<WebSecurityUrl> getAll() {
        return repository.findAll();
    }

    @Override
    public WebSecurityUrl update(long id, WebSecurityUrl webSecurityUrl) {

        WebSecurityUrl existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Security URL not found"));

        existing.setUrl(webSecurityUrl.getUrl() != null ? webSecurityUrl.getUrl() : existing.getUrl());

        return repository.save(existing);
    }

}