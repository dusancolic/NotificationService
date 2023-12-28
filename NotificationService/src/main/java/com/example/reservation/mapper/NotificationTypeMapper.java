package com.example.reservation.mapper;

import com.example.reservation.domain.NotificationType;
import com.example.reservation.dto.NotificationTypeCreateDto;
import com.example.reservation.dto.NotificationTypeDto;
import com.example.reservation.dto.NotificationTypeUpdateDto;
import org.springframework.stereotype.Component;

@Component
public class NotificationTypeMapper {

    public NotificationTypeDto notificationTypeToNotificationTypeDto(NotificationType notificationType)
    {
        NotificationTypeDto notificationTypeDto = new NotificationTypeDto();
        notificationTypeDto.setName(notificationType.getName());
        notificationTypeDto.setText(notificationType.getText());
        notificationTypeDto.setId(notificationType.getId());
        return notificationTypeDto;
    }

    public NotificationType notificationTypeCreateDtoToNotificationType(NotificationTypeCreateDto notificationTypeCreateDto)
    {
        NotificationType notificationType = new NotificationType();
        notificationType.setName(notificationTypeCreateDto.getName());
        notificationType.setText(notificationTypeCreateDto.getText());
        return notificationType;
    }

    public NotificationType notificationTypeUpdateDtoToNotificationType(NotificationType notificationType, NotificationTypeUpdateDto notificationTypeUpdateDto)
    {
        if(notificationTypeUpdateDto.getName() != null)
            notificationType.setName(notificationTypeUpdateDto.getName());
        if(notificationTypeUpdateDto.getText() != null)
            notificationType.setText(notificationTypeUpdateDto.getText());
        return notificationType;
    }
}
