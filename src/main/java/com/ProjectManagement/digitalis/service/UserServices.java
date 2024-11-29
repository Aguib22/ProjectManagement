package com.ProjectManagement.digitalis.service;


import com.ProjectManagement.digitalis.entitie.User;
import com.ProjectManagement.digitalis.exception.UserError;
import com.ProjectManagement.digitalis.dto.RegisterRequest;

import java.util.List;

public interface UserServices {

    User saveUser(User user) throws UserError;

    Boolean registUser(RegisterRequest registerRequest) ;

    User editUser(Long idUser, RegisterRequest userRequest) throws UserError;

    User getUser(Long idUser) throws UserError;

    List<User> listUsers() throws UserError;

    void deleteUser(Long idUser) throws UserError;
}
