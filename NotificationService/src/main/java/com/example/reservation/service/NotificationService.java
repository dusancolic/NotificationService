package com.example.reservation.service;


import com.example.reservation.dto.NotificationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface NotificationService {
    Page<NotificationDto> findAll(Pageable pageable);
    Page<NotificationDto> findAllInRange(LocalDate beginDate, LocalDate endDate, Pageable pageable);
    Page<NotificationDto> findAllByType(String type, Pageable pageable);
    Page<NotificationDto> findAllByEmail(String email, Pageable pageable);
    Page<NotificationDto> findAllInRangeAndByEmail(LocalDate start,LocalDate end,String email, Pageable pageable);
    Page<NotificationDto> findAllByTypeAndByEmail(String type, String email, Pageable pageable);
    Page<NotificationDto> findAllByTypeAndInRange(String type, LocalDate beginDate, LocalDate endDate, Pageable pageable);
    Page<NotificationDto> findAllByTypeAndByEmailAndInRange(String type, String email, LocalDate beginDate, LocalDate endDate, Pageable pageable);

}
