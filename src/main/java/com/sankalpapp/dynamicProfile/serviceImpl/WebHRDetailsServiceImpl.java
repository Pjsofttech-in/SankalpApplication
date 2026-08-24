package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebHRDetails;
import com.sankalpapp.dynamicProfile.repository.WebHRDetailsRepository;
import com.sankalpapp.dynamicProfile.service.WebHRDetailsService;
import com.sankalpapp.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebHRDetailsServiceImpl implements WebHRDetailsService {

    @Autowired
    private WebHRDetailsRepository repository;

    private String normalizeUrl(String url) {
        if (url == null) return "";
        return url.split(",")[0].trim().toLowerCase();
    }

    @Override
    public WebHRDetails create(WebHRDetails webHRDetails, String url) {
        webHRDetails.setUrl(url);
        return repository.save(webHRDetails);
    }

    @Override
    public List<WebHRDetails> getAllByBranchCode(String url) {
        //validateUrlExists;
        return repository.findAllOrderById();
    }


    @Override
    public WebHRDetails getById(Long id, String url) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WebHRDetails not found"));
    }

    @Override
    public WebHRDetails update(Long id, WebHRDetails webHRDetails, String url) {
        WebHRDetails existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WebHRDetails not found"));

        existing.setHrName(webHRDetails.getHrName() != null ? webHRDetails.getHrName() : existing.getHrName());
        existing.setEmail(webHRDetails.getEmail() != null ? webHRDetails.getEmail() : existing.getEmail());
        existing.setContact(webHRDetails.getContact() != null ? webHRDetails.getContact() : existing.getContact());

        return repository.save(existing);
    }

    @Override
    public void delete(Long id, String url) {
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WebHRDetails not found"));
        repository.deleteById(id);
    }
}