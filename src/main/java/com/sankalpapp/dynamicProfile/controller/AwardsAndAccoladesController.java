package com.sankalpapp.dynamicProfile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankalpapp.dynamicProfile.entity.WebAwardsAndAccolades;
import com.sankalpapp.dynamicProfile.service.AwardsAndAccoladesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class AwardsAndAccoladesController {

    @Autowired
    private AwardsAndAccoladesService service;

    @PostMapping(value = "/createAward", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebAwardsAndAccolades> createAward(
            @RequestPart("award") String awardJson,
            @RequestParam String url,
            @RequestParam("awardImageName") MultipartFile awardImageFile) throws JsonProcessingException {


        WebAwardsAndAccolades award = new ObjectMapper().readValue(awardJson, WebAwardsAndAccolades.class);

        return ResponseEntity.ok(service.createAward(award, awardImageFile, url));
    }

    @GetMapping("/getAllAwards")
    public ResponseEntity<List<WebAwardsAndAccolades>> getAllAwardsByBranchCode(
            @RequestParam String url) {

        return ResponseEntity.ok(service.getAllAwardsByBranchCode(url));
    }

    @GetMapping("/getAwardById/{id}")
    public ResponseEntity<WebAwardsAndAccolades> getAwardById(@PathVariable Long id,
                                                              @RequestParam String url) {
        return ResponseEntity.ok(service.getAwardById(id, url));
    }

    @PutMapping(value = "/updateAward/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebAwardsAndAccolades> updateAward(@PathVariable Long id,
                                                             @RequestPart("award") String awardJson,
                                                             @RequestPart(value = "awardImage", required = false) MultipartFile awardImage,
                                                             @RequestParam String url) throws JsonProcessingException {

        WebAwardsAndAccolades award = new ObjectMapper().readValue(awardJson, WebAwardsAndAccolades.class);
        return ResponseEntity.ok(service.updateAward(id, award, awardImage, url));
    }

    @DeleteMapping("/deleteAward/{id}")
    public ResponseEntity<String> deleteAward(@PathVariable Long id,
                                              @RequestParam String url) {
        service.deleteAward(id, url);
        return ResponseEntity.ok("Award deleted successfully");
    }

}