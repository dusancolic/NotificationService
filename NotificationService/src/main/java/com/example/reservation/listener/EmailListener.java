package com.example.reservation.listener;

import com.example.reservation.dto.EmailDto;
import com.example.reservation.dto.NotificationDto;
import com.example.reservation.listener.helper.MessageHelper;
import com.example.reservation.service.EmailService;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
public class EmailListener {

    private MessageHelper messageHelper;
    private EmailService emailService;

    public EmailListener(MessageHelper messageHelper, EmailService emailService) {
        this.messageHelper = messageHelper;
        this.emailService = emailService;
    }

    @JmsListener(destination = "${destination.sendEmails}", concurrency = "5-10")
    public void sendEmail(Message message) throws JMSException {
        EmailDto emailDto = messageHelper.getMessage(message, EmailDto.class);
        System.out.println(emailDto);
        emailService.sendSimpleMessage(emailDto.getEmail()
                , emailDto.getType()
                , emailDto.getParams());
    }
}
