package com.ProjectManagement.digitalis.controller;


import com.ProjectManagement.digitalis.dto.*;
import com.ProjectManagement.digitalis.entitie.Notification;
import com.ProjectManagement.digitalis.entitie.Role;
import com.ProjectManagement.digitalis.entitie.User;
import com.ProjectManagement.digitalis.exception.UserError;
import com.ProjectManagement.digitalis.repositorie.UserRepository;
import com.ProjectManagement.digitalis.service.EmailService;
import com.ProjectManagement.digitalis.service.NotificationService;
import com.ProjectManagement.digitalis.service.UserTokenFmsService;
import com.ProjectManagement.digitalis.service.serviceIntreface.ReunionServices;
import com.ProjectManagement.digitalis.service.serviceIntreface.UserServices;
import com.ProjectManagement.digitalis.service.UserServicesImpl;
import com.ProjectManagement.digitalis.utils.JwtUtilService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/user")
public class ApiUserController {


    private final UserServices userServices;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final JwtUtilService jwtUtilService;
    private final UserServicesImpl userServiceImpl;
    private final UserTokenFmsService userTokenFmsService;
    private final ReunionServices reunionServices;

    private final NotificationService notificationService;

    public ApiUserController(UserServices userServices, UserRepository userRepository, EmailService emailService, JwtUtilService jwtUtilService, UserServicesImpl userServiceImpl, UserTokenFmsService userTokenFmsService, ReunionServices reunionServices, NotificationService notificationService) {
        this.userServices = userServices;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.jwtUtilService = jwtUtilService;
        this.userServiceImpl = userServiceImpl;
        this.userTokenFmsService = userTokenFmsService;
        this.reunionServices = reunionServices;
        this.notificationService = notificationService;
    }

    @PostMapping("/sign-up")
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
    public ResponseEntity<?> userLogin(@RequestBody LoginRequest loginRequest) {

        User authentifedUser = userServiceImpl.login(loginRequest);

        String token = jwtUtilService.generateToken(authentifedUser);
        /*if (authentifedUser.getTemporaryPassword()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authentifedUser);
        }*/
        UserResponse userResponse = new UserResponse(
                authentifedUser.getIdUser(),
                authentifedUser.getPrenomUser(),
                authentifedUser.getNomUser(),
                authentifedUser.getMailUser(),
                authentifedUser.getRole(),
                authentifedUser.isTemporaryPassword()
        );

        LoginResponse loginResponse = LoginResponse.builder().
                token(token).expiresIn(jwtUtilService.getExpirationTime())
                .user(userResponse)
                .build();

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

    @GetMapping("/get-userByRole")
    public ResponseEntity<List<User>> getUserByRole(@RequestParam Role role){
        List<User> users = userServices.getUserByRole(role);
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping("/save-token")
    public ResponseEntity<String> saveTokenFmc(@RequestBody UserTokenFmcDto userTokenFmcDto){
        userTokenFmsService.saveUserToken(userTokenFmcDto);
        return ResponseEntity.ok().body("token enregistré avec succès! "+ userTokenFmcDto.getToken());
    }

    @GetMapping("/notif/{userId}")
    public List<Notification> getUserNotifications(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId);
    }

    @PutMapping("notif/mark-as-read/{id}")
    public void markNotifAsRead(@PathVariable Long id){

            notificationService.makAsRead(id);
    }
}