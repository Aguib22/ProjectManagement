package com.ProjectManagement.digitalis.entitie;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; private String message;
    private Long userId;
    private Boolean isRead = false;
    private LocalDateTime times;
}
