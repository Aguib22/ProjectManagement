package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.Controller.Request.UserRequest;
import com.ProjectManagement.digitalis.Entities.User;
import com.ProjectManagement.digitalis.Exception.UserError;
import com.ProjectManagement.digitalis.dto.RegisterRequest;

import java.util.List;

public interface UserServices {

    User saveUser(User user) throws UserError;

    Boolean registUser(RegisterRequest registerRequest) ;



    User editUser(Long idUser, UserRequest userRequest) throws UserError;

    User getUser(Long idUser) throws UserError;

    List<User> listUsers() throws UserError;

    void deleteUser(Long idUser) throws UserError;
}
