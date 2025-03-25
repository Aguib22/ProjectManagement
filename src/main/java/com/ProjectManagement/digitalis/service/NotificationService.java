package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.entitie.Notification;
import com.ProjectManagement.digitalis.repositorie.NotificationRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Data
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;
    private final FireBaseMessaginService firebaseMessagingService; // 🔥 Ajout de Firebase

    public NotificationService(NotificationRepository notificationRepository, FireBaseMessaginService firebaseMessagingService) {
        this.notificationRepository = notificationRepository;
        this.firebaseMessagingService = firebaseMessagingService;
    }

    //  Création d'une notification avec Firebase
    public void createNotification(String message, Long userId, String userToken) {
        logger.info("Création d'une notification pour l'utilisateur ID: {}", userId);

        // 1 Enregistrer la notification en base de données
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUserId(userId);
        notification.setTimes(LocalDateTime.now());
        notificationRepository.save(notification);

        logger.info("Notification enregistrée pour l'utilisateur ID: {}", userId);

        // 2 Envoyer la notification via Firebase (si le token est disponible)
        if (userToken != null && !userToken.isEmpty()) {
            String response = firebaseMessagingService.sendNotification(userToken, "Nouvelle Notification", message);
            logger.info("Notification envoyée via Firebase: {}", response);
        } else {
            logger.warn("Aucun token FCM disponible pour l'utilisateur ID: {}", userId);
        }
    }

    //  Méthode pour envoyer une notification lorsqu'une tâche est assignée
    public void notifyTaskAssigned(String taskName, Long developerId, String userToken) {
        String message = "Une nouvelle tâche vous a été assignée : " + taskName;
        this.createNotification(message, developerId, userToken);
    }

    //  Méthode pour récupérer les notifications non lues
    public List<Notification> getUserNotifications(Long userId) {
        logger.info("Récupération des notifications non lues pour l'utilisateur ID: {}", userId);
        List<Notification> notifications = notificationRepository.findByUserIdAndIsReadFalse(userId);
        logger.info("Nombre de notifications non lues récupérées pour l'utilisateur {}: {}", userId, notifications.size());
        return notifications;
    }

    // Méthode pour envoyer une notification lorsqu'une tâche est envoyée pour test
    public void notifyTaskSentForTesting(String taskName, Long testerId, String userToken) {
        String message = "Une tâche est prête pour test : " + taskName;
        this.createNotification(message, testerId, userToken);
    }

    //  Méthode pour envoyer une notification lorsqu'un bug est signalé
    public void notifyBugReported(String taskName, Long developerId, String userToken) {
        String message = "Un bug a été signalé sur la tâche : " + taskName;
        this.createNotification(message, developerId, userToken);
    }

    //  Méthode pour envoyer une notification lorsqu'un bug est corrigé
    public void notifyBugFixed(String taskName, Long testerId, String userToken) {
        String message = "Le bug sur la tâche a été corrigé : " + taskName;
        this.createNotification(message, testerId, userToken);
    }

    // Méthode pour envoyer une notification lorsqu'une tâche est terminée
    public void notifyTaskCompleted(String taskName, Long developerId, String userToken) {
        String message = "La tâche a été marquée comme terminée : " + taskName;
        this.createNotification(message, developerId, userToken);
    }

    public void makAsRead(Long notificationId){
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(()->new RuntimeException("aucune notif ne correspond"));

        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

}
