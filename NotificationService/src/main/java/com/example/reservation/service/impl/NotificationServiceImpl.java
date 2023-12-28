package com.example.reservation.service.impl;

import com.example.reservation.domain.NotificationType;
import com.example.reservation.dto.NotificationDto;
import com.example.reservation.mapper.NotificationMapper;
import com.example.reservation.repository.NotificationRepository;
import com.example.reservation.service.NotificationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
    private NotificationMapper notificationMapper;
    private NotificationRepository notificationRepository;

    @Override
    public Page<NotificationDto> findAll(Pageable pageable) {
        return notificationRepository.findAll(pageable).map(notificationMapper::notificationToNotificationDto);
    }

    @Override
    public Page<NotificationDto> findAllInRange(LocalDate start, LocalDate end,Pageable pageable) {
        return notificationRepository.findAllInRange(start, end, pageable).map(notificationMapper::notificationToNotificationDto);
    }

    @Override
    public Page<NotificationDto> findAllByType(String type,Pageable pageable) {
        return notificationRepository.findAllByType(type, pageable).map(notificationMapper::notificationToNotificationDto);
    }

    @Override
    public Page<NotificationDto> findAllByEmail(String email, Pageable pageable) {

        return notificationRepository.findAllByEmail(email, pageable).map(notificationMapper::notificationToNotificationDto);
    }

    @Override
    public Page<NotificationDto> findAllInRangeAndByEmail(LocalDate start, LocalDate end,String email, Pageable pageable) {

        return notificationRepository.findAllInRangeAndByEmail(start, end, email, pageable).map(notificationMapper::notificationToNotificationDto);
    }

    @Override
    public Page<NotificationDto> findAllByTypeAndByEmail(String type, String email, Pageable pageable) {
        return notificationRepository.findAllByTypeAndByEmail(type, email, pageable).map(notificationMapper::notificationToNotificationDto);
    }

    @Override
    public Page<NotificationDto> findAllByTypeAndInRange(String type, LocalDate beginDate, LocalDate endDate, Pageable pageable) {
        return notificationRepository.findAllByTypeAndInRange(beginDate, endDate,type, pageable).map(notificationMapper::notificationToNotificationDto);
    }

    @Override
    public Page<NotificationDto> findAllByTypeAndByEmailAndInRange(String type, String email, LocalDate beginDate, LocalDate endDate, Pageable pageable) {
        return notificationRepository.findAllByTypeAndByEmailAndInRange(type, email, beginDate, endDate, pageable).map(notificationMapper::notificationToNotificationDto);
    }

}
