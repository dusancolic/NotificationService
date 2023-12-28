package com.example.reservation.controller;

import com.example.reservation.dto.*;
import com.example.reservation.service.NotificationService;
import com.example.reservation.service.NotificationTypeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
public class NotificationController {

    private NotificationService notificationService;
    private NotificationTypeService notificationTypeService;

    @GetMapping
    public ResponseEntity<Page<NotificationDto>> getAllNotifications(String authorization, Pageable pageable)
    {
        return new ResponseEntity<>(notificationService.findAll(pageable),HttpStatus.OK);
    }
    @GetMapping("/filter/{string}")
    public ResponseEntity<Page<NotificationDto>> getAllNotificationByString(String authorization, @PathVariable String string, Pageable pageable)
    {
        if(string.contains("@"))
            return new ResponseEntity<>(notificationService.findAllByEmail(string, pageable),HttpStatus.OK);
        return new ResponseEntity<>(notificationService.findAllByType(string, pageable), HttpStatus.OK);
    }
    @GetMapping("/filter/r-")
    public ResponseEntity<Page<NotificationDto>> getAllNotificationsInRange(String authorization,@RequestBody @Valid RangeDto rangeDto, Pageable pageable)
    {
        try {
            LocalDate date = LocalDate.parse(rangeDto.getBegin());
            LocalDate date1 = LocalDate.parse(rangeDto.getEnd());
            return new ResponseEntity<>(notificationService.findAllInRange(date, date1, pageable), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/filter/{string}/{string1}")
    public ResponseEntity<Page<NotificationDto>> getAllNotificationByStrings(String authorization, @PathVariable String string, @PathVariable String string1, Pageable pageable)
    {
        if(string.contains("@"))
            return new ResponseEntity<>(notificationService.findAllByTypeAndByEmail(string1, string, pageable),HttpStatus.OK);
        return new ResponseEntity<>(notificationService.findAllByTypeAndByEmail(string, string1, pageable),HttpStatus.OK);
    }
    @GetMapping("/filter/r-{string}")
    public ResponseEntity<Page<NotificationDto>> getAllNotificationByStringAndInRange(String authorization, @PathVariable String string
            , @RequestBody @Valid RangeDto rangeDto, Pageable pageable) {
        try{
            LocalDate date = LocalDate.parse(rangeDto.getBegin());
            LocalDate date1 = LocalDate.parse(rangeDto.getEnd());
            if(string.contains("@"))
                return new ResponseEntity<>(notificationService.findAllInRangeAndByEmail(date, date1, string, pageable),HttpStatus.OK);
            return new ResponseEntity<>(notificationService.findAllByTypeAndInRange(string, date, date1, pageable),HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/filter/r-{string}/{string1}")
    public ResponseEntity<Page<NotificationDto>> getAllNotificationByStringsAndInRange(String authorization, @PathVariable String string
            , @PathVariable String string1, @RequestBody @Valid RangeDto rangeDto, Pageable pageable) {
        try{
            LocalDate date = LocalDate.parse(rangeDto.getBegin());
            LocalDate date1 = LocalDate.parse(rangeDto.getEnd());
            if(string.contains("@"))
                return new ResponseEntity<>(notificationService.findAllByTypeAndByEmailAndInRange(string1, string, date, date1, pageable),HttpStatus.OK);
            return new ResponseEntity<>(notificationService.findAllByTypeAndByEmailAndInRange(string, string1, date, date1, pageable),HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    // TODO treba tacno taj id da se koristi 
    /*
    @GetMapping("/manager")
    public ResponseEntity<Page<NotificationDto>> getAllNotificationsM(String authorization, Pageable pageable)
    {
        Long id = 1L;
        return new ResponseEntity<>(notificationService.findAllManager(id, pageable),HttpStatus.OK);
    }
    // TODO treba tacno taj id da se koristi 
    @GetMapping("/client")
    public ResponseEntity<Page<NotificationDto>> getAllNotificationsC(String authorization, Pageable pageable)
    {
        Long id = 1L;
        return new ResponseEntity<>(notificationService.findAllClient(id, pageable),HttpStatus.OK);
    }*/

    //NOTIFICATION TYPES
    
    @GetMapping("/type")
    public ResponseEntity<Page<NotificationTypeDto>> getAllNotificationTypes(String authorization, Pageable pageable)
    {
        return new ResponseEntity<>(notificationTypeService.findAll(pageable),HttpStatus.OK);
    }
    @DeleteMapping("/type/{id}")
    public void deleteNotificationType(@PathVariable Long id)
    {
        notificationTypeService.deleteNotificationType(id);
    }

    @PutMapping("/type")
    public ResponseEntity<NotificationTypeDto> updateNotificationType(@RequestBody @Valid NotificationTypeUpdateDto notificationTypeUpdateDto)
    {
        return new ResponseEntity<>(notificationTypeService.updateNotificationType(notificationTypeUpdateDto),HttpStatus.OK);
    }
    @PostMapping("/type")
    public ResponseEntity<NotificationTypeDto> addNotificationType(@RequestBody @Valid NotificationTypeCreateDto notificationTypeCreateDto)
    {
        return new ResponseEntity<>(notificationTypeService.addNotificationType(notificationTypeCreateDto),HttpStatus.OK);
    }

}
