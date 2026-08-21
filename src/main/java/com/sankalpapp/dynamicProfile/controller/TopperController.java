package com.sankalpapp.dynamicProfile.controller;

import com.sankalpapp.dynamicProfile.entity.WebTopper;
import com.sankalpapp.dynamicProfile.service.TopperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class TopperController {

    @Autowired
    private TopperService service;

    @PostMapping("/createTopper")
    public ResponseEntity<WebTopper> createTopper(@RequestPart("topper") WebTopper webTopper,
                                                  @RequestPart(value = "topperImage", required = false) MultipartFile topperImage,
                                                  @RequestParam String role,
                                                  @RequestParam String email,
                                                  @RequestParam String url) {
        return ResponseEntity.ok(service.createTopper(webTopper, role, email, topperImage, url));
    }

    @GetMapping("/getAllToppers")
    public ResponseEntity<List<WebTopper>> getAllToppersByBranchCode(@RequestParam String role,
                                                                     @RequestParam(required = false) String email,
                                                                     @RequestParam String branchCode,
                                                                     @RequestParam String url) {
        return ResponseEntity.ok(service.getAllToppersByBranchCode(role, email, branchCode, url));
    }

    @GetMapping("/getTopperById/{id}")
    public ResponseEntity<WebTopper> getTopperById(@PathVariable Long id,
                                                   @RequestParam String role,
                                                   @RequestParam String email,
                                                   @RequestParam String url) {
        return ResponseEntity.ok(service.getTopperById(id, role, email, url));
    }

    @PutMapping("/updateTopper/{id}")
    public ResponseEntity<WebTopper> updateTopper(@PathVariable Long id,
                                                  @RequestPart("topper") WebTopper webTopper,
                                                  @RequestPart(value = "topperImage", required = false) MultipartFile topperImage,
                                                  @RequestParam String role,
                                                  @RequestParam String email,
                                                  @RequestParam String url) {
        return ResponseEntity.ok(service.updateTopper(id, webTopper, role, email, topperImage, url));
    }

    @DeleteMapping("/deleteTopper/{id}")
    public ResponseEntity<String> deleteTopper(@PathVariable Long id,
                                               @RequestParam String role,
                                               @RequestParam String email,
                                               @RequestParam String url) {
        service.deleteTopper(id, role, email, url);
        return ResponseEntity.ok("Topper deleted successfully");
    }
}