package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.ContactUs;
import com.sankalpapp.dynamicProfile.repository.ContactUsRepository;
import com.sankalpapp.dynamicProfile.service.ContactUsService;
import org.springframework.stereotype.Service;

@Service
public class ContactUsServiceImpl implements ContactUsService {

    private final ContactUsRepository contactUsRepository;

    public ContactUsServiceImpl(ContactUsRepository contactUsRepository) {
        this.contactUsRepository = contactUsRepository;
    }

    @Override
    public ContactUs create(ContactUs contactUs) {
        return contactUsRepository.save(contactUs);
    }

    @Override
    public ContactUs get() {
        return contactUsRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Contact information not found"));
    }

    @Override
    public ContactUs update(ContactUs contactUs) {
        ContactUs existingContactUs = contactUsRepository.findById(contactUs.getId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Contact information not found with id: " + contactUs.getId()
                        ));

        existingContactUs.setAddress(contactUs.getAddress());
        existingContactUs.setContactNo(contactUs.getContactNo());
        existingContactUs.setEmail(contactUs.getEmail());
        existingContactUs.setMapLink(contactUs.getMapLink());

        return contactUsRepository.save(existingContactUs);
    }

    @Override
    public void delete(Long id) {
        ContactUs contactUs = contactUsRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Contact information not found with id: " + id
                        ));

        contactUsRepository.delete(contactUs);
    }
}