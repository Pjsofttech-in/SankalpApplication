package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {
}