package com.example.reservation.mapper;

import com.example.reservation.domain.Notification;
import com.example.reservation.domain.NotificationType;
import com.example.reservation.dto.NotificationCreateDto;
import com.example.reservation.dto.NotificationDto;
import com.example.reservation.dto.NotificationTypeDto;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationDto notificationToNotificationDto(Notification notification)
    {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setId(notification.getId());
        notificationDto.setNotificationTypeDto(new NotificationTypeDto(notification.getType().getId(), notification.getType().getText(), notification.getType().getName()));
        notificationDto.setParameters(notification.getParameters());
        notificationDto.setEmail(notification.getEmail());
        return notificationDto;
    }
    public Notification notificationCreateDtoToNotification(NotificationCreateDto notificationCreateDto)
    {
        Notification notification = new Notification();
        notification.setEmail(notificationCreateDto.getEmail());
        notification.setParameters(notificationCreateDto.getParameters());
        notification.setType(new NotificationType(notificationCreateDto.getNotificationTypeDto().getText()
                , notificationCreateDto.getNotificationTypeDto().getName()));
        return notification;
    }

}
