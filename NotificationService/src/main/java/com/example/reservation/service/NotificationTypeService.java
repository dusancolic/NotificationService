package com.example.reservation.service;

import com.example.reservation.dto.NotificationTypeCreateDto;
import com.example.reservation.dto.NotificationTypeDto;
import com.example.reservation.dto.NotificationTypeUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationTypeService {
    Page<NotificationTypeDto> findAll(Pageable pageable);
    NotificationTypeDto updateNotificationType(NotificationTypeUpdateDto notificationTypeUpdateDto);
    void deleteNotificationType(Long id);
    NotificationTypeDto addNotificationType(NotificationTypeCreateDto notificationTypeCreateDto);
}
