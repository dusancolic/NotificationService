package com.example.reservation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class NotificationDto {
    private Long id;
    private String email;
    private NotificationTypeDto notificationTypeDto;
    private List<String> parameters;

    public NotificationDto(NotificationTypeDto notificationTypeDto, List<String> parameters) {
        this.notificationTypeDto = notificationTypeDto;
        this.parameters = parameters;
    }
}
