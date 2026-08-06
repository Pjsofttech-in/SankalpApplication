package com.sankalpapp.serviceimpl;

import com.sankalpapp.entity.Contact;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.repository.ContactRepository;
import com.sankalpapp.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    // Save Contact
    @Override
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    // Update Contact
    @Override
    public Contact updateContact(Long id, Contact contact) {

        Contact existingContact = getContactById(id);

        existingContact.setName(contact.getName());
        existingContact.setEmail(contact.getEmail());
        existingContact.setMobile(contact.getMobile());
        existingContact.setMessage(contact.getMessage());
        existingContact.setReplied(contact.getReplied());

        return contactRepository.save(existingContact);
    }

    // Delete Contact
    @Override
    public void deleteContact(Long id) {

        Contact contact = getContactById(id);
        contactRepository.delete(contact);
    }

    // Get Contact By Id
    @Override
    public Contact getContactById(Long id) {

        return contactRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contact not found with id : " + id));
    }

    // Get All Contacts
    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
}