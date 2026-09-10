package com.sankalpapp.dynamicProfile.controller;

import com.sankalpapp.dynamicProfile.entity.FAQ;
import com.sankalpapp.dynamicProfile.service.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faqs")
@RequiredArgsConstructor
public class FAQController {

    private final FAQService faqService;

    @GetMapping
    public ResponseEntity<List<FAQ>> getAllFAQs() {

        return ResponseEntity.ok(
                faqService.getAllFAQs()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FAQ> getFAQById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                faqService.getFAQById(id)
        );
    }

    @PostMapping
    public ResponseEntity<FAQ> createFAQ(
            @RequestBody FAQ faq) {

        FAQ savedFAQ = faqService.createFAQ(faq);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedFAQ);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FAQ> updateFAQ(
            @PathVariable Long id,
            @RequestBody FAQ faq) {

        FAQ updatedFAQ =
                faqService.updateFAQ(id, faq);

        return ResponseEntity.ok(updatedFAQ);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFAQ(
            @PathVariable Long id) {

        faqService.deleteFAQ(id);

        return ResponseEntity.noContent().build();
    }
}