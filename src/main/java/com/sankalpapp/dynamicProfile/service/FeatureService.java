package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.Feature;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FeatureService {

    Feature createFeature(
            Feature feature,
            MultipartFile imageFile,
            String url
    );

    List<Feature> getAllFeatures(String url);

    Feature getFeatureById(
            Long id,
            String url
    );

    Feature updateFeature(
            Long id,
            Feature feature,
            MultipartFile imageFile,
            String url
    );

    void deleteFeature(
            Long id,
            String url
    );
}