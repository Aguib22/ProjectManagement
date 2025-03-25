package com.ProjectManagement.digitalis.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @author Aguibou sow
 * @date 2025-03-19 16:14
 * @package com.ProjectManagement.digitalis.service
 * @project digitalis
 */
@Service
@Data
public class FireBaseMessaginService {
    public String sendNotification(String token, String title, String body) {
        try {
            Message message = Message.builder()
                    .putData("title", title)
                    .putData("body", body)
                    .setToken(token) // 🔥 Envoi au token FCM de l'utilisateur
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            return "Notification envoyée avec succès : " + response;
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de l'envoi de la notification";
        }
    }
}
