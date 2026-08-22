package com.sankalpapp.dynamicProfile.controller;

import com.sankalpapp.dynamicProfile.entity.WebFooter;
import com.sankalpapp.dynamicProfile.service.FooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FooterController {

    @Autowired
    private FooterService service;

    @PostMapping("/createFooter")
    public ResponseEntity<WebFooter> createFooter(@RequestBody WebFooter webFooter,
                                                  @RequestParam(required = false) String url) {
        return ResponseEntity.ok(service.createFooter(webFooter, url));
    }

    @GetMapping("/getAllFooters")
    public ResponseEntity<List<WebFooter>> getAllFootersByBranchCode(@RequestParam(required = false) String url) {
        return ResponseEntity.ok(service.getAllFootersByBranchCode(url));
    }

    @GetMapping("/getFooterById/{id}")
    public ResponseEntity<WebFooter> getFooterById(@PathVariable Long id,
                                                   @RequestParam(required = false) String url) {
        return ResponseEntity.ok(service.getFooterById(id, url));
    }

    @PutMapping("/updateFooter/{id}")
    public ResponseEntity<WebFooter> updateFooter(@PathVariable Long id,
                                                  @RequestBody WebFooter webFooter,
                                                  @RequestParam(required = false) String url) {
        return ResponseEntity.ok(service.updateFooter(id, webFooter, url));
    }

    @DeleteMapping("/deleteFooter/{id}")
    public ResponseEntity<String> deleteFooter(@PathVariable Long id,
                                               @RequestParam(required = false) String url) {
        service.deleteFooter(id, url);
        return ResponseEntity.ok("Footer deleted successfully");
    }

}