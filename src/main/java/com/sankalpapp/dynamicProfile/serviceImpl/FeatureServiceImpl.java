package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.Feature;
import com.sankalpapp.dynamicProfile.repository.FeatureRepository;
import com.sankalpapp.dynamicProfile.service.FeatureService;
import com.sankalpapp.serviceimpl.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FeatureServiceImpl implements FeatureService {

    private final static String folder = "Feature";
    private final FeatureRepository featureRepository;
    private final S3Service s3service;

    public FeatureServiceImpl(
            FeatureRepository featureRepository, S3Service s3service) {

        this.featureRepository = featureRepository;
        this.s3service = s3service;
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

        uploadFile(imageFile, feature);

        return featureRepository.save(feature);
    }

    private void uploadFile(MultipartFile pdf, Feature feature) {
        if (pdf != null) {
            try {
                String fileURL = s3service.uploadFile(pdf, folder);
                feature.setImage(fileURL);
            } catch (IOException e) {
                throw new RuntimeException("Unable to upload File");
            }
        }
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

        uploadFile(imageFile, feature);

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

        s3service.deleteFileByUrl(existingFeature.getImage());

        featureRepository.delete(existingFeature);
    }
}