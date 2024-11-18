package com.ProjectManagement.digitalis.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class LoginRequest {


    private String mailUser;


    private String password;
}
