package com.aidan.apptcg.notification.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NotificationService {
    private final JavaMailSender mailSender;

    public void notifyUser(String email, String confirmedToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete Registration to App TCG!");
        mailMessage.setFrom("MySyna.pro@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:4200/confirm?confirmationToken="+ confirmedToken);
        mailSender.send(mailMessage);
    }

    public void sendPasswordResetEmail(String email, String confirmedToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Reset your password App TCG!");
        mailMessage.setFrom("MySyna.pro@gmail.com");
        mailMessage.setText("To reset your password, please click here : "
                +"http://localhost:4200/changePassword?confirmationToken="+ confirmedToken);
        mailSender.send(mailMessage);
    }
}
