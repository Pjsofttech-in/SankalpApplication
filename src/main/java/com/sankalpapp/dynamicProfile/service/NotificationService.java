package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.Notification;

import java.util.List;

public interface NotificationService {

    Notification create(Notification notification);

    List<Notification> getAll();

    Notification getById(Long id);

    void delete(Long id);
}