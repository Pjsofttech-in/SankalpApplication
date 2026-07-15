package com.testapplication.controller;

import com.testapplication.entity.Contact;
import com.testapplication.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ContactController {

    private final ContactService contactService;

    // Save Contact
    @PostMapping
    public Contact saveContact(@RequestBody Contact contact) {
        return contactService.saveContact(contact);
    }

    // Get All Contacts
    @GetMapping
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    // Get Contact By Id
    @GetMapping("/{id}")
    public Contact getContactById(@PathVariable Long id) {
        return contactService.getContactById(id);
    }

    // Update Contact
    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable Long id,
                                 @RequestBody Contact contact) {
        return contactService.updateContact(id, contact);
    }

    // Delete Contact
    @DeleteMapping("/{id}")
    public String deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return "Contact deleted successfully.";
    }
}