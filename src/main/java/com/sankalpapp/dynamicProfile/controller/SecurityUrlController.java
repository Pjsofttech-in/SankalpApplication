package com.sankalpapp.dynamicProfile.controller;

import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.security.JwtUtil;
import com.sankalpapp.dynamicProfile.service.SecurityUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class SecurityUrlController {

    @Autowired
    private SecurityUrlService service;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/createSecurityUrl")
    public ResponseEntity<WebSecurityUrl> create(@RequestBody WebSecurityUrl webSecurityUrl,
                                                 @RequestParam String role,
                                                 @RequestParam String email) {
        return ResponseEntity.ok(service.create(webSecurityUrl,role,email));
    }

    @GetMapping("/getAllSecurityUrls")
    public ResponseEntity<List<WebSecurityUrl>> getAllByBranchCode(@RequestParam String role,
                                                                   @RequestParam(required = false) String email,
                                                                   @RequestParam String branchCode) {
        return ResponseEntity.ok(service.getAllByBranchCode(role, email, branchCode));
    }
    @PutMapping("/updateSecurityUrl/{id}")
    public ResponseEntity<WebSecurityUrl> update(@PathVariable long id,
                                                 @RequestBody WebSecurityUrl webSecurityUrl,
                                                 @RequestParam String role,
                                                 @RequestParam String email) {
        return ResponseEntity.ok(service.update(id, webSecurityUrl, role, email));
    }

    @GetMapping("/getTokenForUser")
    public ResponseEntity<?> generateTokenByUrl(@RequestParam String url) {
        String token = jwtUtil.generateTokenFromUrl(url);
        String branchCode = service.getBranchCodeByUrl(url);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("branchCode", branchCode);

        return ResponseEntity.ok(response);
    }

}