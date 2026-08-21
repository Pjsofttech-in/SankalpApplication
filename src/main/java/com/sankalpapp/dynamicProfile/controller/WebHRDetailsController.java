package com.sankalpapp.dynamicProfile.controller;

import com.sankalpapp.dynamicProfile.entity.WebHRDetails;
import com.sankalpapp.dynamicProfile.service.WebHRDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class WebHRDetailsController {

    @Autowired
    private WebHRDetailsService service;

    @PostMapping("/createWebHR")
    public ResponseEntity<WebHRDetails> create(@RequestBody WebHRDetails webHRDetails,
                                               @RequestParam String role,
                                               @RequestParam String email,
                                               @RequestParam String url) {
        return ResponseEntity.ok(service.create(webHRDetails, role, email, url));
    }

    @GetMapping("/getAllWebHR")
    public ResponseEntity<List<WebHRDetails>> getAllByBranchCode(@RequestParam String role,
                                                                 @RequestParam(required = false) String email,
                                                                 @RequestParam String url,
                                                                 @RequestParam String branchCode) {
        return ResponseEntity.ok(service.getAllByBranchCode(role, email, url, branchCode));
    }

    @GetMapping("/getWebHRById/{id}")
    public ResponseEntity<WebHRDetails> getById(@PathVariable Long id,
                                                @RequestParam String role,
                                                @RequestParam(required = false) String email,
                                                @RequestParam String url) {
        return ResponseEntity.ok(service.getById(id, role, email, url));
    }

    @PutMapping("/updateWebHR/{id}")
    public ResponseEntity<WebHRDetails> update(@PathVariable Long id,
                                               @RequestBody WebHRDetails webHRDetails,
                                               @RequestParam String role,
                                               @RequestParam String email,
                                               @RequestParam String url) {
        return ResponseEntity.ok(service.update(id, webHRDetails, role, email, url));
    }

    @DeleteMapping("/deleteWebHR/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id,
                                         @RequestParam String role,
                                         @RequestParam String email,
                                         @RequestParam String url) {
        service.delete(id, role, email, url);
        return ResponseEntity.ok("WebHRDetails deleted successfully");
    }
}