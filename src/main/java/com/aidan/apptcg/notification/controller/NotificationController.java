package com.aidan.apptcg.notification.controller;

import com.aidan.apptcg.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class NotificationController implements NotificationControllerApi{

    private final NotificationService notificationService;

    public ResponseEntity<String> sendRegistrationEmail(String email, String confirmationToken) {
        try {
            notificationService.notifyUser(email, confirmationToken);
            return ResponseEntity.ok("Email de confirmation envoyé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }
    }

    public ResponseEntity<String> sendPasswordResetEmail(String email, String confirmationToken) {
        try {
            notificationService.sendPasswordResetEmail(email, confirmationToken);
            return ResponseEntity.ok("Email de réinitialisation envoyé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }
    }
}


