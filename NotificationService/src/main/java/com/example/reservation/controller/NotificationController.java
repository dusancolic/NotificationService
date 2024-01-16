package com.example.reservation.controller;

import com.example.reservation.dto.*;
import com.example.reservation.security.CheckSecurity;
import com.example.reservation.security.service.TokenService;
import com.example.reservation.service.EmailService;
import com.example.reservation.service.NotificationService;
import com.example.reservation.service.NotificationTypeService;
import io.jsonwebtoken.Claims;
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
import java.util.List;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
public class NotificationController {

    private NotificationService notificationService;
    private NotificationTypeService notificationTypeService;
    private TokenService tokenService;
    private EmailService emailService;
    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_CLIENT", "ROLE_MANAGER"})
    public ResponseEntity<Page<NotificationDto>> getAllNotifications(@RequestHeader("Authorization") String authorization,
                                                                     Pageable pageable)
    {
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        if(claims.get("role").equals("ROLE_ADMIN"))
            return new ResponseEntity<>(notificationService.findAll(pageable),HttpStatus.OK);
        return new ResponseEntity<>(notificationService.findAllByEmail(claims.get("email").toString(), pageable),HttpStatus.OK);
    }
    @GetMapping("/filter/{string}")
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_CLIENT", "ROLE_MANAGER"})
    public ResponseEntity<Page<NotificationDto>> getAllNotificationByString(@RequestHeader("Authorization") String authorization,
                                                                            @PathVariable String string, Pageable pageable)
    {
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        boolean admin = claims.get("role").equals("ROLE_ADMIN");
        if(string.contains("@") && admin)
            return new ResponseEntity<>(notificationService.findAllByEmail(string, pageable),HttpStatus.OK);
        if(!string.contains("@") && admin)
            return new ResponseEntity<>(notificationService.findAllByType(string, pageable), HttpStatus.OK);
        if(string.contains("@"))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(notificationService.findAllByTypeAndByEmail(string, claims.get("email").toString(), pageable), HttpStatus.OK);
    }
    @GetMapping("/filter/r-")
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_CLIENT", "ROLE_MANAGER"})
    public ResponseEntity<Page<NotificationDto>> getAllNotificationsInRange(@RequestHeader("Authorization") String authorization,
                                                                            @RequestBody @Valid RangeDto rangeDto, Pageable pageable)
    {
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        boolean admin = claims.get("role").equals("ROLE_ADMIN");
        try {
            LocalDate date = LocalDate.parse(rangeDto.getBegin());
            LocalDate date1 = LocalDate.parse(rangeDto.getEnd());
            if(admin)
                return new ResponseEntity<>(notificationService.findAllInRange(date, date1, pageable), HttpStatus.OK);
            return new ResponseEntity<>(notificationService.findAllInRangeAndByEmail(date, date1,claims.get("email").toString(), pageable), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/filter/{string}/{string1}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Page<NotificationDto>> getAllNotificationByStrings(@RequestHeader("Authorization") String authorization,
                                                                             @PathVariable String string, @PathVariable String string1, Pageable pageable)
    {
        if(string.contains("@"))
            return new ResponseEntity<>(notificationService.findAllByTypeAndByEmail(string1, string, pageable),HttpStatus.OK);
        return new ResponseEntity<>(notificationService.findAllByTypeAndByEmail(string, string1, pageable),HttpStatus.OK);
    }
    @GetMapping("/filter/r-{string}")
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_CLIENT", "ROLE_MANAGER"})
    public ResponseEntity<Page<NotificationDto>> getAllNotificationByStringAndInRange(@RequestHeader("Authorization") String authorization,
                                                                                      @PathVariable String string
            , @RequestBody @Valid RangeDto rangeDto, Pageable pageable) {
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        boolean admin = claims.get("role").equals("ROLE_ADMIN");
        try{
            LocalDate date = LocalDate.parse(rangeDto.getBegin());
            LocalDate date1 = LocalDate.parse(rangeDto.getEnd());
            if(string.contains("@") && admin)
                return new ResponseEntity<>(notificationService.findAllInRangeAndByEmail(date, date1, string, pageable),HttpStatus.OK);
            if(!string.contains("@") && admin)
                return new ResponseEntity<>(notificationService.findAllByTypeAndInRange(string, date, date1, pageable),HttpStatus.OK);
            if(string.contains("@"))
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(notificationService.findAllByTypeAndByEmailAndInRange(string, claims.get("email").toString(), date, date1, pageable),HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/filter/r-{string}/{string1}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Page<NotificationDto>> getAllNotificationByStringsAndInRange(@RequestHeader("Authorization") String authorization, @PathVariable String string
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

    //NOTIFICATION TYPES

    @GetMapping("/type")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Page<NotificationTypeDto>> getAllNotificationTypes(@RequestHeader("Authorization") String authorization,
                                                                             Pageable pageable)
    {
        return new ResponseEntity<>(notificationTypeService.findAll(pageable),HttpStatus.OK);
    }
    @DeleteMapping("/type/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public void deleteNotificationType(@RequestHeader("Authorization") String authorization,
                                       @PathVariable Long id)
    {
        if(id<=5)
            return;
        notificationTypeService.deleteNotificationType(id);
    }

    @PutMapping("/type")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<NotificationTypeDto> updateNotificationType(@RequestHeader("Authorization") String authorization,
                                                                      @RequestBody @Valid NotificationTypeUpdateDto notificationTypeUpdateDto)
    {
        String name = notificationTypeUpdateDto.getOldName();
        if(name.equals("activation") || name.equals("booking") || name.equals("cancellation)") || name.equals("reminder") || name.equals("passwordChange"))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(notificationTypeService.updateNotificationType(notificationTypeUpdateDto),HttpStatus.OK);
    }
    @PostMapping("/type")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<NotificationTypeDto> addNotificationType(@RequestHeader("Authorization") String authorization,
                                                                   @RequestBody @Valid NotificationTypeCreateDto notificationTypeCreateDto)
    {
        return new ResponseEntity<>(notificationTypeService.addNotificationType(notificationTypeCreateDto),HttpStatus.OK);
    }
    @GetMapping("/em")
    public ResponseEntity<String> sendEmail()
    {
        emailService.sendSimpleMessage("ucolic3021rn@raf.rs", "reminder", List.of("Uros","Colic","19:00"));
        return new ResponseEntity<>("Sent.", HttpStatus.OK);
    }
}
