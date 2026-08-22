package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.ContactUs;

public interface ContactUsService {

    ContactUs create(ContactUs contactUs);

    ContactUs get();

    ContactUs update(ContactUs contactUs);

    void delete(Long id);
}