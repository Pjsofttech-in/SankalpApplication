package com.sankalpapp.dynamicProfile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankalpapp.dynamicProfile.dto.WebJobCareerOptionDTO;
import com.sankalpapp.dynamicProfile.entity.WebJobCareerOption;
import com.sankalpapp.dynamicProfile.service.JobCareerOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class JobCareerOptionController {

    @Autowired
    private JobCareerOptionService service;

    @PostMapping(value = "/createJobCareerOption", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebJobCareerOptionDTO> create(
            @RequestPart("job") String jobJson,
            @RequestPart("resumeFile") MultipartFile resumeFile,
            @RequestParam(required = false) String url,
            @RequestParam Long webHRDetailsId) throws JsonProcessingException {

        WebJobCareerOption job = new ObjectMapper().readValue(jobJson, WebJobCareerOption.class);
        return ResponseEntity.ok(service.create(job, resumeFile, url, webHRDetailsId));
    }

    @GetMapping("/getAllJobCareerOptions")
    public ResponseEntity<List<WebJobCareerOptionDTO>> getAllByBranchCode(

            @RequestParam(required = false) String url) {
        return ResponseEntity.ok(service.getAllByBranchCode(url));
    }

    @GetMapping("/getJobCareerOptionById/{id}")
    public ResponseEntity<WebJobCareerOptionDTO> getById(
            @PathVariable Long id,
            @RequestParam(required = false) String url) {
        return ResponseEntity.ok(service.getById(id, url));
    }

    @PutMapping(value = "/updateJobCareerOption/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebJobCareerOptionDTO> update(
            @PathVariable Long id,
            @RequestPart("job") String jobJson,
            @RequestPart(value = "resumeFile", required = false) MultipartFile resumeFile,
            @RequestParam(required = false) String url,
            @RequestParam(required = false) Long webHRDetailsId) throws Exception {

        WebJobCareerOption job = new ObjectMapper().readValue(jobJson, WebJobCareerOption.class);
        return ResponseEntity.ok(service.update(id, job, resumeFile, url, webHRDetailsId));
    }

    @DeleteMapping("/deleteJobCareerOption/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id,
            @RequestParam(required = false) String url) {
        service.delete(id, url);
        return ResponseEntity.ok("Job post deleted successfully");
    }
}