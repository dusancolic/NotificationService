package com.example.reservation.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RangeDto {
    @Temporal(TemporalType.DATE)
    @NotNull
    private String begin;
    @Temporal(TemporalType.DATE)
    @NotNull
    private String end;
}
