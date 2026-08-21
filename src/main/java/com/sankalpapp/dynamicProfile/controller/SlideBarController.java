package com.sankalpapp.dynamicProfile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankalpapp.dynamicProfile.entity.WebSlideBar;
import com.sankalpapp.dynamicProfile.service.SlideBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class SlideBarController {

    @Autowired
    private SlideBarService service;

    @PostMapping("/createSlideBar")
    public ResponseEntity<WebSlideBar> createSlideBar(@RequestParam("slideBar") String slideBarJson,
                                                      @RequestParam String role,
                                                      @RequestParam String email,
                                                      @RequestParam String url,
                                                      @RequestParam(value = "slideBarImages", required = false) List<MultipartFile> slideBarImages)
            throws JsonProcessingException {

        WebSlideBar webSlideBar = new ObjectMapper().readValue(slideBarJson, WebSlideBar.class);
        return ResponseEntity.ok(service.createSlideBar(webSlideBar, role, email, slideBarImages, url));
    }

    @GetMapping("/getAllSlideBars")
    public ResponseEntity<List<WebSlideBar>> getAllSlideBarsByBranchCode(@RequestParam String role,
                                                                         @RequestParam(required = false) String email,
                                                                         @RequestParam String branchCode,
                                                                         @RequestParam String url) {
        return ResponseEntity.ok(service.getAllByBranchCode(role, email, branchCode, url));
    }

    @GetMapping("/getSlideBarById/{id}")
    public ResponseEntity<WebSlideBar> getSlideBarById(@PathVariable Long id,
                                                       @RequestParam String role,
                                                       @RequestParam String email,
                                                       @RequestParam String url) {
        return ResponseEntity.ok(service.getSlideBarById(id, role, email, url));
    }

    @PutMapping("/updateSlideBar/{id}")
    public ResponseEntity<WebSlideBar> updateSlideBar(@PathVariable Long id,
                                                      @RequestParam(value = "slideBar", required = false) String slideBarJson,
                                                      @RequestParam String role,
                                                      @RequestParam String email,
                                                      @RequestParam String url,
                                                      @RequestParam(value = "newImages", required = false) List<MultipartFile> newImages,
                                                      @RequestParam(value = "deleteImages", required = false) List<MultipartFile> deleteImageFiles)
            throws JsonProcessingException {

        // Parse slideBar if provided
        WebSlideBar webSlideBar = (slideBarJson != null && !slideBarJson.isBlank())
                ? new ObjectMapper().readValue(slideBarJson, WebSlideBar.class)
                : new WebSlideBar();

        // Extract filenames from delete image files
        List<String> deleteImageNames = deleteImageFiles != null
                ? deleteImageFiles.stream()
                .map(MultipartFile::getOriginalFilename)
                .filter(name -> name != null && !name.isBlank())
                .toList()
                : null;

        WebSlideBar updated = service.updateSlideBar(id, webSlideBar, role, email, newImages, deleteImageNames, url);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/deleteSlideBar/{id}")
    public ResponseEntity<String> deleteSlideBar(@PathVariable Long id,
                                                 @RequestParam String role,
                                                 @RequestParam String email,
                                                 @RequestParam String url) {
        service.deleteSlideBar(id, role, email, url);
        return ResponseEntity.ok("SlideBar deleted successfully");
    }
}
