package com.ProjectManagement.digitalis.Controller.Request;


import com.ProjectManagement.digitalis.Entities.Reunion;
import com.ProjectManagement.digitalis.Entities.Role;
import com.ProjectManagement.digitalis.Entities.SousTache;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private Long matriculeUser;
    private String prenomUser;
    private String nomUser;
    private int numeroUser;
    private String mailUser;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String role;

    private Long idReunion;
}
