package com.example.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationCreateDto {
    @NotBlank
    private NotificationTypeDto notificationTypeDto;
    @NotBlank
    private List<String> parameters;
    @NotBlank
    private String email;
}
