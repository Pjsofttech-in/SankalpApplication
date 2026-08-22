package com.sankalpapp.dynamicProfile.controller;

import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.dynamicProfile.service.SecurityUrlService;
import com.sankalpapp.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SecurityUrlController {

    @Autowired
    private SecurityUrlService service;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/createSecurityUrl")
    public ResponseEntity<WebSecurityUrl> create(@RequestBody WebSecurityUrl webSecurityUrl) {
        return ResponseEntity.ok(service.create(webSecurityUrl));
    }

    @GetMapping("/getAllSecurityUrls")
    public ResponseEntity<List<WebSecurityUrl>> getAllByBranchCode() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/updateSecurityUrl/{id}")
    public ResponseEntity<WebSecurityUrl> update(@PathVariable long id,
                                                 @RequestBody WebSecurityUrl webSecurityUrl) {
        return ResponseEntity.ok(service.update(id, webSecurityUrl));
    }

    @GetMapping("/getTokenForUser")
    public ResponseEntity<?> generateTokenByUrl(@RequestParam(required = false) String url) {
        String token = jwtUtil.generateTokenFromUrl(url);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

}