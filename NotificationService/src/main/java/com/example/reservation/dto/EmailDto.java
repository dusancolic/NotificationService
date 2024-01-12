package com.example.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {
    private String email;
    private String type;
    private List<String> params;

    @Override
    public String toString() {
        return "EmailDto{" +
                "email='" + email + '\'' +
                ", type='" + type + '\'' +
                ", params=" + params +
                '}';
    }
}
