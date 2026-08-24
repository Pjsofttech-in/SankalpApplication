package com.sankalpapp.dynamicProfile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankalpapp.dynamicProfile.entity.Feature;
import com.sankalpapp.dynamicProfile.service.FeatureService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class FeatureController {

    private final FeatureService featureService;

    public FeatureController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @PostMapping(
            value = "/createFeature",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Feature> createFeature(
            @RequestPart("feature") String featureJson,
            @RequestParam(required = false) String url,
            @RequestPart("featureImage") MultipartFile imageFile
    ) throws JsonProcessingException {

        Feature feature =
                new ObjectMapper().readValue(
                        featureJson,
                        Feature.class
                );

        return ResponseEntity.ok(
                featureService.createFeature(
                        feature,
                        imageFile,
                        url
                )
        );
    }

    @GetMapping("/getAllFeatures")
    public ResponseEntity<List<Feature>> getAllFeatures(
            @RequestParam(required = false) String url) {

        return ResponseEntity.ok(
                featureService.getAllFeatures(url)
        );
    }

    @GetMapping("/getFeatureById/{id}")
    public ResponseEntity<Feature> getFeatureById(
            @PathVariable Long id,
            @RequestParam(required = false) String url) {

        return ResponseEntity.ok(
                featureService.getFeatureById(
                        id,
                        url
                )
        );
    }

    @PutMapping(
            value = "/updateFeature/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Feature> updateFeature(
            @PathVariable Long id,
            @RequestPart("feature") String featureJson,
            @RequestParam(required = false) String url,
            @RequestPart(value = "featureImage", required = false)
            MultipartFile imageFile
    ) throws JsonProcessingException {

        Feature feature =
                new ObjectMapper().readValue(
                        featureJson,
                        Feature.class
                );

        return ResponseEntity.ok(
                featureService.updateFeature(
                        id,
                        feature,
                        imageFile,
                        url
                )
        );
    }

    @DeleteMapping("/deleteFeature/{id}")
    public ResponseEntity<String> deleteFeature(
            @PathVariable Long id,
            @RequestParam(required = false) String url) {

        featureService.deleteFeature(id, url);

        return ResponseEntity.ok(
                "Feature deleted successfully"
        );
    }
}