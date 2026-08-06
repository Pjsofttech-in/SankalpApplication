package com.sankalpapp.service;

import com.sankalpapp.entity.Contact;

import java.util.List;

public interface ContactService {

    Contact saveContact(Contact contact);

    Contact updateContact(Long id, Contact contact);

    void deleteContact(Long id);

    Contact getContactById(Long id);

    List<Contact> getAllContacts();
}