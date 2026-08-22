package com.sankalpapp.dynamicProfile.controller;

import com.sankalpapp.dynamicProfile.entity.WebCounter;
import com.sankalpapp.dynamicProfile.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CounterController {

    @Autowired
    private CounterService service;

    @PostMapping("/createCounter")
    public ResponseEntity<WebCounter> createCounter(@RequestBody WebCounter webCounter,
                                                    @RequestParam String role,
                                                    @RequestParam String email,
                                                    @RequestParam(required = false) String url) {
        return ResponseEntity.ok(service.createCounter(webCounter, url));
    }

    @GetMapping("/getAllCounters")
    public ResponseEntity<List<WebCounter>> getAllCountersByBranchCode(

            @RequestParam(required = false) String url) {
        return ResponseEntity.ok(service.getAllByBranchCode(url));
    }

    @GetMapping("/getCounterById/{id}")
    public ResponseEntity<WebCounter> getCounterById(@PathVariable Long id,
                                                     @RequestParam String role,
                                                     @RequestParam String email,
                                                     @RequestParam(required = false) String url) {
        return ResponseEntity.ok(service.getCounterById(id, url));
    }

    @PutMapping("/updateCounter/{id}")
    public ResponseEntity<WebCounter> updateCounter(@PathVariable Long id,
                                                    @RequestBody WebCounter webCounter,
                                                    @RequestParam String role,
                                                    @RequestParam String email,
                                                    @RequestParam(required = false) String url) {
        return ResponseEntity.ok(service.updateCounter(id, webCounter, url));
    }

    @DeleteMapping("/deleteCounter/{id}")
    public ResponseEntity<String> deleteCounter(@PathVariable Long id,
                                                @RequestParam String role,
                                                @RequestParam String email,
                                                @RequestParam(required = false) String url) {
        service.deleteCounter(id, url);
        return ResponseEntity.ok("Counter deleted successfully");
    }
}