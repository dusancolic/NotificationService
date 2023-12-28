package com.example.reservation.service.impl;

import com.example.reservation.domain.NotificationType;
import com.example.reservation.dto.NotificationTypeCreateDto;
import com.example.reservation.dto.NotificationTypeDto;
import com.example.reservation.dto.NotificationTypeUpdateDto;
import com.example.reservation.mapper.NotificationTypeMapper;
import com.example.reservation.repository.NotificationTypeRepository;
import com.example.reservation.service.NotificationTypeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@Service
@Transactional
public class NotificationTypeServiceImpl implements NotificationTypeService {

    private NotificationTypeRepository notificationTypeRepository;
    private NotificationTypeMapper notificationTypeMapper;
    @Override
    public Page<NotificationTypeDto> findAll(Pageable pageable) {
        return notificationTypeRepository.findAll(pageable).map(notificationTypeMapper::notificationTypeToNotificationTypeDto);
    }

    @Override
    public NotificationTypeDto updateNotificationType(NotificationTypeUpdateDto notificationTypeUpdateDto) {
        NotificationType notificationType = notificationTypeRepository.findByName(notificationTypeUpdateDto.getOldName())
                .orElseThrow(() -> new RuntimeException("No such notification type!"));
        notificationType = notificationTypeMapper.notificationTypeUpdateDtoToNotificationType(notificationType, notificationTypeUpdateDto);
        notificationTypeRepository.save(notificationType);
        return notificationTypeMapper.notificationTypeToNotificationTypeDto(notificationType);

    }

    @Override
    public void deleteNotificationType(Long id) {
        notificationTypeRepository.deleteById(id);
    }

    @Override
    public NotificationTypeDto addNotificationType(NotificationTypeCreateDto notificationTypeCreateDto) {
        NotificationType notificationType = notificationTypeMapper.notificationTypeCreateDtoToNotificationType(notificationTypeCreateDto);
        notificationTypeRepository.save(notificationType);
        return notificationTypeMapper.notificationTypeToNotificationTypeDto(notificationType);
    }
}
