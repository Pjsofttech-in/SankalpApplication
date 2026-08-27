package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.HeroSection;
import com.sankalpapp.dynamicProfile.repository.HeroSectionRepository;
import com.sankalpapp.dynamicProfile.service.HeroSectionService;
import com.sankalpapp.serviceimpl.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class HeroSectionServiceImpl implements HeroSectionService {

    private final HeroSectionRepository heroSectionRepository;
    private final S3Service s3Service;
    private static final String folder = "HeroSection";

    public HeroSectionServiceImpl(
            HeroSectionRepository heroSectionRepository, S3Service s3Service) {

        this.heroSectionRepository = heroSectionRepository;
        this.s3Service = s3Service;
    }

    @Override
    public HeroSection createHeroSection(
            HeroSection heroSection,
            MultipartFile imageFile,
            String url) {

        uploadFile(imageFile, heroSection);

        return heroSectionRepository.save(heroSection);
    }

    private void uploadFile(MultipartFile pdf, HeroSection obj) {
        if (pdf != null) {
            try {
                String fileURL = s3Service.uploadFile(pdf, folder);
                obj.setImage(fileURL);
            } catch (IOException e) {
                throw new RuntimeException("Unable to upload File");
            }
        }
    }

    @Override
    public List<HeroSection> getAllHeroSections(String url) {

        return heroSectionRepository.findAll();
    }

    @Override
    public HeroSection getHeroSectionById(
            Long id,
            String url) {

        return heroSectionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Hero Section not found with id: " + id
                        )
                );
    }

    @Override
    public HeroSection updateHeroSection(
            Long id,
            HeroSection heroSection,
            MultipartFile imageFile,
            String url) {

        HeroSection existingHeroSection =
                heroSectionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Hero Section not found with id: " + id
                                )
                        );

        existingHeroSection.setTitle(
                heroSection.getTitle()
        );

        existingHeroSection.setDescription(
                heroSection.getDescription()
        );

        existingHeroSection.setLink(
                heroSection.getLink()
        );

        existingHeroSection.setPriority(
                heroSection.getPriority()
        );

        uploadFile(imageFile, existingHeroSection);

        return heroSectionRepository.save(
                existingHeroSection
        );
    }

    @Override
    public void deleteHeroSection(
            Long id,
            String url) {

        HeroSection existingHeroSection =
                heroSectionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Hero Section not found with id: " + id
                                )
                        );
        s3Service.deleteFileByUrl(existingHeroSection.getImage());

        heroSectionRepository.delete(existingHeroSection);
    }
}