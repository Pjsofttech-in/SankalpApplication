package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebCounter;
import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.dynamicProfile.repository.CounterRepository;
import com.sankalpapp.dynamicProfile.repository.SecurityUrlrepository;
import com.sankalpapp.dynamicProfile.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CounterServiceImpl implements CounterService {

    @Autowired
    private CounterRepository repository;

    @Autowired
    private SecurityUrlrepository securityUrlRepository;

    private void validateUrlExists(String url) {

        String normalizedUrl = normalizeUrl(url);
        securityUrlRepository.findByUrl(normalizedUrl)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "URL [" + url + "] is not allowed"
                ));
    }


    private String normalizeUrl(String url) {
        return (url == null) ? "" : url.split(",")[0].trim().toLowerCase();
    }

    @Override
    public WebCounter createCounter(WebCounter webCounter, String url) {
         WebSecurityUrl webSecurityUrl = securityUrlRepository.findByUrl(normalizeUrl(url))
                .orElseThrow(() -> new ResourceNotFoundException("URL not found"));

        webCounter.setWebSecurityUrl(webSecurityUrl);
        webCounter.setUrl(url);

        return repository.save(webCounter);
    }

    @Override
    public List<WebCounter> getAllByBranchCode(String url) {
         return repository.findAllOrderById();
    }


    @Override
    public WebCounter updateCounter(Long id, WebCounter webCounter, String url) {
         WebCounter existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Counter not found"));

        // Update values
        existing.setCounterName1(webCounter.getCounterName1());
        existing.setCountValue1(webCounter.getCountValue1());
        existing.setCounterColor1(webCounter.getCounterColor1());

        existing.setCounterName2(webCounter.getCounterName2());
        existing.setCountValue2(webCounter.getCountValue2());
        existing.setCounterColor2(webCounter.getCounterColor2());

        existing.setCounterName3(webCounter.getCounterName3());
        existing.setCountValue3(webCounter.getCountValue3());
        existing.setCounterColor3(webCounter.getCounterColor3());

        existing.setUrl(webCounter.getUrl());

        return repository.save(existing);
    }

    @Override
    public void deleteCounter(Long id, String url) {
         WebCounter webCounter = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Counter not found"));

        repository.delete(webCounter);
    }

    @Override
    public WebCounter getCounterById(Long id, String url) {
         return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Counter not found"));
    }
}