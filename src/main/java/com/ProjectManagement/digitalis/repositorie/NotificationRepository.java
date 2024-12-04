package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findByUserIdAndIsReadFalse(Long userId);
}
