package com.ProjectManagement.digitalis.Controller;

import com.ProjectManagement.digitalis.Controller.Request.UserRequest;
import com.ProjectManagement.digitalis.Entities.Reunion;
import com.ProjectManagement.digitalis.Entities.Role;
import com.ProjectManagement.digitalis.Entities.User;
import com.ProjectManagement.digitalis.Exception.ReunionError;
import com.ProjectManagement.digitalis.Exception.UserError;
import com.ProjectManagement.digitalis.Services.ReunionServices;
import com.ProjectManagement.digitalis.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class ApiUserController {

    @Autowired
    private UserServices userServices;
    @Autowired
    private ReunionServices reunionServices;

    @PostMapping("/save")
    public User saveUser(@RequestBody UserRequest userRequest) throws UserError, ReunionError {

        User user = new User();
        user.setMatriculeUser(userRequest.getMatriculeUser());
        user.setPrenomUser(userRequest.getPrenomUser());
        user.setNomUser(userRequest.getNomUser());
        user.setNumeroUser(userRequest.getNumeroUser());
        user.setMailUser(userRequest.getMailUser());
        user.setPassword(userRequest.getPassword());
        user.setCreatedAt(user.getCreatedAt());
        user.setUpdatedAt(userRequest.getUpdatedAt());
        user.setRole(Role.valueOf(userRequest.getRole()));

        Reunion reunion = reunionServices.getReunion(userRequest.getIdReunion());
        user.setReunion(reunion);

        return userServices.saveUser(user);
    }

    @GetMapping("/get/{idUser}")
    public User getUser(@PathVariable Long idUser) throws UserError {
        return userServices.getUser(idUser);
    }

    @GetMapping("/get/liste")
    public List<User> listUsers() throws UserError {
        return userServices.listUsers();
    }

    @DeleteMapping("/delete/{idUser}")
    public void deleteUser(@PathVariable Long idUser) throws UserError {
        userServices.deleteUser(idUser);
    }

    @PutMapping("/edit/{idUser}")
    public User editUser(@RequestBody UserRequest userRequest, @PathVariable Long idUser) throws UserError {
        return userServices.editUser(idUser, userRequest);
    }
}
