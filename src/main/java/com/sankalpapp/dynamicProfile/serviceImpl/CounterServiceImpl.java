package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebCounter;
import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.dynamicProfile.repository.CounterRepository;
import com.sankalpapp.dynamicProfile.service.CounterService;
import com.sankalpapp.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CounterServiceImpl implements CounterService {

    @Autowired
    private CounterRepository repository;


    private String normalizeUrl(String url) {
        return (url == null) ? "" : url.split(",")[0].trim().toLowerCase();
    }

    @Override
    public WebCounter createCounter(WebCounter webCounter, String url) {
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