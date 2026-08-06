package com.sankalpapp.controller;

import com.sankalpapp.entity.Contact;
import com.sankalpapp.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ContactController {

    private final ContactService contactService;

    // Save Contact (Public)
    @PostMapping
    @PreAuthorize("permitAll()")
    public Contact saveContact(@RequestBody Contact contact) {

        return contactService.saveContact(contact);
    }

    // Get All Contacts
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Contact> getAllContacts() {

        return contactService.getAllContacts();
    }

    // Get Contact By Id
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Contact getContactById(@PathVariable Long id) {

        return contactService.getContactById(id);
    }

    // Update Contact
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Contact updateContact(@PathVariable Long id,
                                 @RequestBody Contact contact) {

        return contactService.updateContact(id, contact);
    }

    // Delete Contact
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteContact(@PathVariable Long id) {

        contactService.deleteContact(id);

        return "Contact deleted successfully.";
    }
}