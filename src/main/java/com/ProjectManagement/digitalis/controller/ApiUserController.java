package com.ProjectManagement.digitalis.controller;


import com.ProjectManagement.digitalis.entitie.User;
import com.ProjectManagement.digitalis.exception.UserError;
import com.ProjectManagement.digitalis.service.EmailService;
import com.ProjectManagement.digitalis.service.ReunionServices;
import com.ProjectManagement.digitalis.service.UserServices;
import com.ProjectManagement.digitalis.service.UserServicesImpl;
import com.ProjectManagement.digitalis.dto.LoginRequest;
import com.ProjectManagement.digitalis.dto.LoginResponse;
import com.ProjectManagement.digitalis.dto.RegisterRequest;
import com.ProjectManagement.digitalis.utils.JwtUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin("*")
public class ApiUserController {

    @Autowired
    private UserServices userServices;

    private final EmailService emailService;
    private final JwtUtilService jwtUtilService;
    private final UserServicesImpl userServiceImpl;
    @Autowired
    private ReunionServices reunionServices;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        Boolean isUserCreate = userServiceImpl.registUser(registerRequest);

        if (isUserCreate) {
            emailService.sendMail(
                    registerRequest.getMailUser(),
                    "**Digitalis** creation de compte ",
                    "Bonjour " + registerRequest.getNomUser() + " " + registerRequest.getPrenomUser() +
                            " Votre compte de Digitalis ProjectManagement à bien été créer et voici vos identifiants de connexion: \n Email: " +
                            registerRequest.getMailUser() + "\n Password: " + registerRequest.getPassword());
            return new ResponseEntity(registerRequest, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> userLogin(@RequestBody LoginRequest loginRequest) {

        User authentifedUser = userServiceImpl.login(loginRequest);

        String token = jwtUtilService.generateToken(authentifedUser);

        LoginResponse loginResponse = LoginResponse.builder().
                token(token).expiresIn(jwtUtilService.getExpirationTime()).build();

        return ResponseEntity.ok(loginResponse);
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
    public User editUser(@RequestBody RegisterRequest registerRequest, @PathVariable Long idUser) throws UserError {
        return userServices.editUser(idUser, registerRequest);

    }
}