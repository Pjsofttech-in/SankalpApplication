package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.Feature;
import com.sankalpapp.dynamicProfile.repository.FeatureRepository;
import com.sankalpapp.dynamicProfile.service.FeatureService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FeatureServiceImpl implements FeatureService {

    private final FeatureRepository featureRepository;

    public FeatureServiceImpl(
            FeatureRepository featureRepository) {

        this.featureRepository = featureRepository;
    }

    @Override
    public Feature createFeature(
            Feature feature,
            MultipartFile imageFile,
            String url) {

        /*
         * R2 image upload will be added here.
         *
         * Example:
         *
         * String imageUrl = r2StorageService.uploadFile(imageFile);
         * feature.setImage(imageUrl);
         */

        if (imageFile != null && !imageFile.isEmpty()) {

            // TODO: Upload image to Cloudflare R2
        }

        return featureRepository.save(feature);
    }

    @Override
    public List<Feature> getAllFeatures(String url) {

        return featureRepository.findAll();
    }

    @Override
    public Feature getFeatureById(
            Long id,
            String url) {

        return featureRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Feature not found with id: " + id
                        )
                );
    }

    @Override
    public Feature updateFeature(
            Long id,
            Feature feature,
            MultipartFile imageFile,
            String url) {

        Feature existingFeature =
                featureRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Feature not found with id: " + id
                                )
                        );

        existingFeature.setTitle(
                feature.getTitle()
        );

        existingFeature.setDescription(
                feature.getDescription()
        );

        existingFeature.setLink(
                feature.getLink()
        );

        /*
         * Only replace the image when a new image
         * has been provided.
         */
        if (imageFile != null && !imageFile.isEmpty()) {

            // TODO: Upload new image to Cloudflare R2

            // String imageUrl =
            //         r2StorageService.uploadFile(imageFile);

            // existingFeature.setImage(imageUrl);
        }

        return featureRepository.save(existingFeature);
    }

    @Override
    public void deleteFeature(
            Long id,
            String url) {

        Feature existingFeature =
                featureRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Feature not found with id: " + id
                                )
                        );

        /*
         * Later, delete the image from R2 here
         * before deleting the database record.
         */

        featureRepository.delete(existingFeature);
    }
}