package com.example.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationTypeUpdateDto {

    private String text;
    private String name;
    @NotBlank
    private String oldName;
}
