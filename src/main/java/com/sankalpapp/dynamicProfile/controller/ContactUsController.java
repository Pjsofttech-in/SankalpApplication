package com.sankalpapp.dynamicProfile.controller;

import com.sankalpapp.dynamicProfile.entity.ContactUs;
import com.sankalpapp.dynamicProfile.service.ContactUsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact-us")
public class ContactUsController {

    private final ContactUsService contactUsService;

    public ContactUsController(ContactUsService contactUsService) {
        this.contactUsService = contactUsService;
    }

    @PostMapping
    public ContactUs create(@RequestBody ContactUs contactUs) {
        return contactUsService.create(contactUs);
    }

    @GetMapping
    public ContactUs get() {
        return contactUsService.get();
    }

    @PutMapping
    public ContactUs update(@RequestBody ContactUs contactUs) {
        return contactUsService.update(contactUs);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        contactUsService.delete(id);
    }
}