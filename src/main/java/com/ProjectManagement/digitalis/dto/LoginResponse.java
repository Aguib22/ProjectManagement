package com.ProjectManagement.digitalis.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String token;

    private long expiresIn;
    private UserResponse user;
    private boolean temporaryPassword;

}
