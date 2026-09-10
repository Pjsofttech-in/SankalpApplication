package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.Notification;
import com.sankalpapp.dynamicProfile.repository.NotificationRepository;
import com.sankalpapp.dynamicProfile.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification create(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification update(Long id, Notification incomingNotification) {
        Notification existingNotification =  notificationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Notification not found"));
        existingNotification.setTitle(incomingNotification.getTitle());
        existingNotification.setDescription(incomingNotification.getDescription());
        return notificationRepository.save(existingNotification);
    }

    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    public Notification getById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Notification not found"));
    }

    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }
}