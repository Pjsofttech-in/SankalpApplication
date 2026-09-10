package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.FAQ;

import java.util.List;

public interface FAQService {

    List<FAQ> getAllFAQs();

    FAQ getFAQById(Long id);

    FAQ createFAQ(FAQ faq);

    FAQ updateFAQ(Long id, FAQ faq);

    void deleteFAQ(Long id);
}