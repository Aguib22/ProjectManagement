package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.entitie.Notification;
import com.ProjectManagement.digitalis.repositorie.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    // Méthode pour créer une notification
    public void createNotifcation(String msg, Long userId) {
        logger.info("Création d'une notification pour l'utilisateur avec l'ID : {}", userId);
        Notification notification = new Notification();
        notification.setMessage(msg);
        notification.setUserId(userId);
        notification.setTimes(LocalDateTime.now());

        notificationRepository.save(notification);
        logger.info("Notification créée et enregistrée pour l'utilisateur : {}", userId);

        // Envoi de la notification via WebSocket
        messagingTemplate.convertAndSendToUser(userId.toString(), "/topic/notification", msg);
        logger.info("Notification envoyée à l'utilisateur avec l'ID : {}", userId);
    }

    // Méthode pour récupérer les notifications non lues d'un utilisateur
    public List<Notification> getUserNotififications(Long userId) {
        logger.info("Récupération des notifications non lues pour l'utilisateur avec l'ID : {}", userId);
        List<Notification> notifications = notificationRepository.findByUserIdAndIsReadFalse(userId);
        logger.info("Nombre de notifications non lues récupérées pour l'utilisateur {} : {}", userId, notifications.size());
        return notifications;
    }
}
