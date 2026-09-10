package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.Notification;

import java.util.List;

public interface NotificationService {

    Notification create(Notification notification);

    Notification update(Long id, Notification incomingNotification);

    List<Notification> getAll();

    Notification getById(Long id);

    void delete(Long id);
}