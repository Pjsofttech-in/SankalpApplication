package com.sankalpapp.dynamicProfile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankalpapp.dynamicProfile.entity.HeroSection;
import com.sankalpapp.dynamicProfile.service.HeroSectionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class HeroSectionController {

    private final HeroSectionService heroSectionService;

    public HeroSectionController(HeroSectionService heroSectionService) {
        this.heroSectionService = heroSectionService;
    }

    @PostMapping(
            value = "/createHeroSection",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<HeroSection> createHeroSection(
            @RequestPart("heroSection") String heroSectionJson,
            @RequestParam(required = false) String url,
            @RequestPart("heroSectionImage") MultipartFile imageFile
    ) throws JsonProcessingException {

        HeroSection heroSection =
                new ObjectMapper().readValue(
                        heroSectionJson,
                        HeroSection.class
                );

        return ResponseEntity.ok(
                heroSectionService.createHeroSection(
                        heroSection,
                        imageFile,
                        url
                )
        );
    }

    @GetMapping("/getAllHeroSections")
    public ResponseEntity<List<HeroSection>> getAllHeroSections(
            @RequestParam(required = false) String url) {

        return ResponseEntity.ok(
                heroSectionService.getAllHeroSections(url)
        );
    }

    @GetMapping("/getHeroSectionById/{id}")
    public ResponseEntity<HeroSection> getHeroSectionById(
            @PathVariable Long id,
            @RequestParam(required = false) String url) {

        return ResponseEntity.ok(
                heroSectionService.getHeroSectionById(id, url)
        );
    }

    @PutMapping(
            value = "/updateHeroSection/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<HeroSection> updateHeroSection(
            @PathVariable Long id,
            @RequestPart("heroSection") String heroSectionJson,
            @RequestParam(required = false) String url,
            @RequestPart(value = "heroSectionImage", required = false)
            MultipartFile imageFile
    ) throws JsonProcessingException {

        HeroSection heroSection =
                new ObjectMapper().readValue(
                        heroSectionJson,
                        HeroSection.class
                );

        return ResponseEntity.ok(
                heroSectionService.updateHeroSection(
                        id,
                        heroSection,
                        imageFile,
                        url
                )
        );
    }

    @DeleteMapping("/deleteHeroSection/{id}")
    public ResponseEntity<String> deleteHeroSection(
            @PathVariable Long id,
            @RequestParam(required = false) String url) {

        heroSectionService.deleteHeroSection(id, url);

        return ResponseEntity.ok(
                "Hero Section deleted successfully"
        );
    }
}