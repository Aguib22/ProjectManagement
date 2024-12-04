package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.entitie.Notification;
import com.ProjectManagement.digitalis.repositorie.NotificationRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    public NotificationService(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void createNotifcation(String msg, Long userId){
        Notification notification = new Notification();
        notification.setMessage(msg);
        notification.setUserId(userId);
        notification.setTimes(LocalDateTime.now());

        notificationRepository.save(notification);

        messagingTemplate.convertAndSendToUser(userId.toString(),"/topic/notification",msg);
    }

    public List<Notification> getUserNotififications(Long userId){
        return notificationRepository.findByUserIdAndIsReadFalse(userId);
    }
}
