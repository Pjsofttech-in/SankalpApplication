package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.HeroSection;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HeroSectionService {

    HeroSection createHeroSection(
            HeroSection heroSection,
            MultipartFile imageFile,
            String url
    );

    List<HeroSection> getAllHeroSections(String url);

    HeroSection getHeroSectionById(
            Long id,
            String url
    );

    HeroSection updateHeroSection(
            Long id,
            HeroSection heroSection,
            MultipartFile imageFile,
            String url
    );

    void deleteHeroSection(
            Long id,
            String url
    );
}