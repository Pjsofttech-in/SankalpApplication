package com.sankalpapp.dynamicProfile.controller;

import com.sankalpapp.dynamicProfile.entity.WebContactForm;
import com.sankalpapp.dynamicProfile.service.ContactFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactFormController {

    @Autowired
    private ContactFormService service;

    @PostMapping("/createContactForm")
    public ResponseEntity<WebContactForm> createContactForm(@RequestBody WebContactForm webContactForm,
                                                            @RequestParam String url) {
        return ResponseEntity.ok(service.create(webContactForm, url));
    }

    @GetMapping("/getAllContactForms")
    public ResponseEntity<List<WebContactForm>> getAllContactFormsByBranchCode(
            
            @RequestParam String url) {
        return ResponseEntity.ok(service.getAllByBranchCode(url));
    }

    @GetMapping("/getContactFormById/{id}")
    public ResponseEntity<WebContactForm> getContactFormById(@PathVariable Long id,
                                                             
                                                             @RequestParam String url) {
        return ResponseEntity.ok(service.getById(id, url));
    }

    @PutMapping("/updateContactForm/{id}")
    public ResponseEntity<WebContactForm> updateContactForm(@PathVariable Long id,
                                                            @RequestBody WebContactForm webContactForm,
                                                            @RequestParam String url) {
        return ResponseEntity.ok(service.update(id, webContactForm, url));
    }

    @DeleteMapping("/deleteContactForm/{id}")
    public ResponseEntity<String> deleteContactForm(@PathVariable Long id,
                                                    @RequestParam String url) {
        service.delete(id, url);
        return ResponseEntity.ok("ContactForm deleted successfully");
    }
}