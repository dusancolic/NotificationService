package com.example.reservation.service;

import com.example.reservation.domain.Notification;
import com.example.reservation.domain.NotificationType;
import com.example.reservation.repository.NotificationRepository;
import com.example.reservation.repository.NotificationTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component
@AllArgsConstructor
public class EmailService {
    public JavaMailSender mailSender;
    private NotificationTypeRepository notificationTypeRepository;
    private NotificationRepository notificationRepository;


    public void sendSimpleMessage(String to, String type, List<String> params) {

        NotificationType notificationType = notificationTypeRepository.findByName(type).get();
        String text = notificationType.getText();
        for(int i = 0; i < params.size(); i++)
            notificationType.setText(notificationType.getText().replace(i + 1 +"s", params.get(i)));
        Notification notification = new Notification(to, notificationType, params);
        notificationRepository.save(notification);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(notificationType.getName());
        message.setText(notificationType.getText());
        notificationType.setText(text);
        mailSender.send(message);
    }
}
