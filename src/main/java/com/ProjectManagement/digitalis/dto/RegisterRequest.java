package com.ProjectManagement.digitalis.dto;

import com.ProjectManagement.digitalis.Entities.UserService;
import lombok.Data;

@Data
public class RegisterRequest {

    private Long matriculeUser;
    private String prenomUser;
    private String nomUser;
    private int numeroUser;
    private String mailUser;
    private String password;
    private String role;
    private UserService service;
}
