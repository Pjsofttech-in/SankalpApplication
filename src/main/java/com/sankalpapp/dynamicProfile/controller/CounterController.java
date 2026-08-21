package com.sankalpapp.dynamicProfile.controller;

import com.sankalpapp.dynamicProfile.entity.WebCounter;
import com.sankalpapp.dynamicProfile.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class CounterController {

    @Autowired
    private CounterService service;

    @PostMapping("/createCounter")
    public ResponseEntity<WebCounter> createCounter(@RequestBody WebCounter webCounter,
                                                    @RequestParam String role,
                                                    @RequestParam String email,
                                                    @RequestParam String url) {
        return ResponseEntity.ok(service.createCounter(webCounter, role, email, url));
    }

    @GetMapping("/getAllCounters")
    public ResponseEntity<List<WebCounter>> getAllCountersByBranchCode(
            @RequestParam String role,
            @RequestParam(required = false) String email,
            @RequestParam String url,
            @RequestParam String branchCode) {
        return ResponseEntity.ok(service.getAllByBranchCode(role, email, url, branchCode));
    }

    @GetMapping("/getCounterById/{id}")
    public ResponseEntity<WebCounter> getCounterById(@PathVariable Long id,
                                                     @RequestParam String role,
                                                     @RequestParam String email,
                                                     @RequestParam String url) {
        return ResponseEntity.ok(service.getCounterById(id, role, email, url));
    }

    @PutMapping("/updateCounter/{id}")
    public ResponseEntity<WebCounter> updateCounter(@PathVariable Long id,
                                                    @RequestBody WebCounter webCounter,
                                                    @RequestParam String role,
                                                    @RequestParam String email,
                                                    @RequestParam String url) {
        return ResponseEntity.ok(service.updateCounter(id, webCounter, role, email, url));
    }

    @DeleteMapping("/deleteCounter/{id}")
    public ResponseEntity<String> deleteCounter(@PathVariable Long id,
                                                @RequestParam String role,
                                                @RequestParam String email,
                                                @RequestParam String url) {
        service.deleteCounter(id, role, email, url);
        return ResponseEntity.ok("Counter deleted successfully");
    }
}