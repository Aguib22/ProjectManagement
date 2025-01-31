package com.ProjectManagement.digitalis.service.serviceIntreface;


import com.ProjectManagement.digitalis.entitie.Role;
import com.ProjectManagement.digitalis.entitie.User;
import com.ProjectManagement.digitalis.exception.UserError;
import com.ProjectManagement.digitalis.dto.RegisterRequest;

import java.util.List;

public interface UserServices {



    Boolean registUser(RegisterRequest registerRequest) ;

    User editUser(Long idUser, RegisterRequest userRequest) throws UserError;

    User getUser(Long idUser) throws UserError;

    List<User> listUsers() throws UserError;

    void deleteUser(Long idUser) throws UserError;

    public List<User> getUserByRole(Role role);
}
