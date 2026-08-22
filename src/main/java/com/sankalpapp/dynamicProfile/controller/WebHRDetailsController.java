package com.sankalpapp.dynamicProfile.controller;

import com.sankalpapp.dynamicProfile.entity.WebHRDetails;
import com.sankalpapp.dynamicProfile.service.WebHRDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WebHRDetailsController {

    @Autowired
    private WebHRDetailsService service;

    @PostMapping("/createWebHR")
    public ResponseEntity<WebHRDetails> create(@RequestBody WebHRDetails webHRDetails,
                                               @RequestParam(required = false) String url) {
        return ResponseEntity.ok(service.create(webHRDetails, url));
    }

    @GetMapping("/getAllWebHR")
    public ResponseEntity<List<WebHRDetails>> getAllByBranchCode(@RequestParam(required = false) String url) {
        return ResponseEntity.ok(service.getAllByBranchCode(url));
    }

    @GetMapping("/getWebHRById/{id}")
    public ResponseEntity<WebHRDetails> getById(@PathVariable Long id,
                                                @RequestParam(required = false) String url) {
        return ResponseEntity.ok(service.getById(id, url));
    }

    @PutMapping("/updateWebHR/{id}")
    public ResponseEntity<WebHRDetails> update(@PathVariable Long id,
                                               @RequestBody WebHRDetails webHRDetails,
                                               @RequestParam(required = false) String url) {
        return ResponseEntity.ok(service.update(id, webHRDetails, url));
    }

    @DeleteMapping("/deleteWebHR/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id,
                                         @RequestParam(required = false) String url) {
        service.delete(id, url);
        return ResponseEntity.ok("WebHRDetails deleted successfully");
    }
}