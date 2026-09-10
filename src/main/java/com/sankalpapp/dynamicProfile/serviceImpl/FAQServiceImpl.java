package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.FAQ;
import com.sankalpapp.dynamicProfile.repository.FAQRepository;
import com.sankalpapp.dynamicProfile.service.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService {

    private final FAQRepository faqRepository;

    @Override
    public List<FAQ> getAllFAQs() {
        return faqRepository.findAll();
    }

    @Override
    public FAQ getFAQById(Long id) {

        return faqRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("FAQ not found with id: " + id)
                );
    }

    @Override
    public FAQ createFAQ(FAQ faq) {

        faq.setId(null);

        return faqRepository.save(faq);
    }

    @Override
    public FAQ updateFAQ(Long id, FAQ faq) {

        FAQ existingFAQ = faqRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("FAQ not found with id: " + id)
                );

        existingFAQ.setQuestion(faq.getQuestion());
        existingFAQ.setAnswer(faq.getAnswer());

        return faqRepository.save(existingFAQ);
    }

    @Override
    public void deleteFAQ(Long id) {

        if (!faqRepository.existsById(id)) {
            throw new RuntimeException(
                    "FAQ not found with id: " + id
            );
        }

        faqRepository.deleteById(id);
    }
}