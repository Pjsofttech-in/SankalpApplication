package com.sankalpapp.dynamicProfile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankalpapp.dynamicProfile.entity.WebAboutUs;
import com.sankalpapp.dynamicProfile.service.AboutUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class AboutUsController {

    @Autowired
    private AboutUsService service;

    @PostMapping(value = "/createAboutUs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebAboutUs> createAboutUs(
            @RequestPart("aboutUs") String aboutUsJson,
            @RequestParam(required = false) String url,
            @RequestParam("aboutUsImageName") MultipartFile aboutUsImage) throws JsonProcessingException {


        WebAboutUs webAboutUs = new ObjectMapper().readValue(aboutUsJson, WebAboutUs.class);

        return ResponseEntity.ok(service.createAboutUs(webAboutUs, aboutUsImage, url));
    }

    @GetMapping("/getAllAboutUs")
    public ResponseEntity<List<WebAboutUs>> getAllAboutUsByBranchCode(

            @RequestParam(required = false) String url) {

        return ResponseEntity.ok(service.getAllAboutUsByBranchCode(url));
    }

    @GetMapping("/getAboutUsById/{id}")
    public ResponseEntity<WebAboutUs> getAboutUsById(@PathVariable int id,
                                                     @RequestParam(required = false) String url) {
        return ResponseEntity.ok(service.getAboutUsById(id, url));
    }

    @PutMapping(value = "/updateAboutUs/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebAboutUs> updateAboutUs(@PathVariable int id,
                                                    @RequestPart("aboutUs") String aboutUsJson,
                                                    @RequestParam(required = false) String url,
                                                    @RequestPart(value = "aboutUsImage", required = false) MultipartFile aboutUsImage) throws JsonProcessingException {

        WebAboutUs webAboutUs = new ObjectMapper().readValue(aboutUsJson, WebAboutUs.class);
        return ResponseEntity.ok(service.updateAboutUs(id, webAboutUs, aboutUsImage, url));
    }

    @DeleteMapping("/deleteAboutUs/{id}")
    public ResponseEntity<String> deleteAboutUs(@PathVariable int id,
                                                @RequestParam String role,
                                                @RequestParam String email,
                                                @RequestParam(required = false) String url) {
        service.deleteAboutUs(id, url);
        return ResponseEntity.ok("AboutUs deleted successfully");
    }

}