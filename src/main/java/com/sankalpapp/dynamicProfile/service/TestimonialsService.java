package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebTestimonials;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TestimonialsService {
    WebTestimonials create(WebTestimonials webTestimonials, String role, String email, MultipartFile testimonialImage, String url);
    List<WebTestimonials> getAllByBranchCode(String role, String email, String branchCode, String url);
    WebTestimonials update(Long id, WebTestimonials webTestimonials, String role, String email, MultipartFile testimonialImage, String url);
    void delete(Long id, String role, String email, String url);
    WebTestimonials getById(Long id, String role, String email, String url);
}