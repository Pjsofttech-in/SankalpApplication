package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.HeroSection;
import com.sankalpapp.dynamicProfile.repository.HeroSectionRepository;
import com.sankalpapp.dynamicProfile.service.HeroSectionService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class HeroSectionServiceImpl implements HeroSectionService {

    private final HeroSectionRepository heroSectionRepository;

    public HeroSectionServiceImpl(
            HeroSectionRepository heroSectionRepository) {

        this.heroSectionRepository = heroSectionRepository;
    }

    @Override
    public HeroSection createHeroSection(
            HeroSection heroSection,
            MultipartFile imageFile,
            String url) {

        /*
         * Image upload logic will go here.
         *
         * For example, when R2 is implemented:
         *
         * String imageUrl = r2StorageService.uploadFile(imageFile);
         * heroSection.setImage(imageUrl);
         */

        if (imageFile != null && !imageFile.isEmpty()) {

            // TODO: Replace this with your R2 upload service
            // String imageUrl = r2StorageService.uploadFile(imageFile);
            // heroSection.setImage(imageUrl);
        }

        return heroSectionRepository.save(heroSection);
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

        /*
         * Only replace the existing image if a new image
         * was actually provided.
         */
        if (imageFile != null && !imageFile.isEmpty()) {

            // TODO: Replace this with your R2 upload service
            // String imageUrl = r2StorageService.uploadFile(imageFile);
            // existingHeroSection.setImage(imageUrl);
        }

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

        /*
         * When R2 is implemented, you can also delete
         * the image from R2 here.
         *
         * if (existingHeroSection.getImage() != null) {
         *     r2StorageService.deleteFile(
         *         existingHeroSection.getImage()
         *     );
         * }
         */

        heroSectionRepository.delete(existingHeroSection);
    }
}