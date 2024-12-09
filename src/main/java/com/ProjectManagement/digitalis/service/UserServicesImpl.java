package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.entitie.UserService;
import com.ProjectManagement.digitalis.repositorie.UserServiceRepository;
import com.ProjectManagement.digitalis.service.serviceIntreface.UserServices;
import org.apache.commons.lang3.RandomStringUtils;


import com.ProjectManagement.digitalis.entitie.Role;
import com.ProjectManagement.digitalis.entitie.User;
import com.ProjectManagement.digitalis.exception.UserError;
import com.ProjectManagement.digitalis.repositorie.ReunionRepository;
import com.ProjectManagement.digitalis.repositorie.UserRepository;
import com.ProjectManagement.digitalis.dto.LoginRequest;
import com.ProjectManagement.digitalis.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class UserServicesImpl implements UserServices {

    private static final Logger logger = LoggerFactory.getLogger(UserServicesImpl.class);
    private final UserRepository userRepository;
    private final UserServiceRepository userServiceRepository;
    private final ReunionRepository reunionRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserServicesImpl(UserRepository userRepository, UserServiceRepository userServiceRepository, ReunionRepository reunionRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userServiceRepository = userServiceRepository;
        this.reunionRepository = reunionRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

   /* @Override
    public User saveUser(User user) throws UserError {
        if (user == null) {
            throw new UserError("L'utilisateur à enregistrer est null");
        }
        return userRepository.save(user);
    }
*/
    @Override
    public Boolean registUser(RegisterRequest registerRequest) {
        logger.info("Tentative d'enregistrement de l'utilisateur avec l'email : {}",registerRequest.getMailUser());
        if ( userRepository.existsByMailUser(registerRequest.getMailUser())){
            logger.warn("Échec de l'inscription : l'utilisateur avec l'email {} existe déjà", registerRequest.getMailUser());
            return false;
        }
        User user = new User();
        UserService service = userServiceRepository.findById(registerRequest.getUserService())
                        .orElseThrow(() -> { logger.error("Le service avec l'id {} n'existe pas", registerRequest.getUserService());
                            return new RuntimeException("le service indiqué n'existe pas"); });


        BeanUtils.copyProperties(registerRequest,user);

        String password = RandomStringUtils.randomAlphabetic(8);
        registerRequest.setPassword(password);
        String hasPassword = passwordEncoder.encode(password);
        user.setUserService(service);
        user.setPassword(hasPassword);
        user.setRole(Role.valueOf(registerRequest.getRole()));

        userRepository.save(user);
        logger.info("Utilisateur avec l'email {} enregistré avec succès", registerRequest.getMailUser());
        return true;
    }

    public User login(LoginRequest loginRequest){
        logger.info("Tentative de connexion pour l'utilisateur avec l'email : {}", loginRequest.getMailUser());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getMailUser(),
                        loginRequest.getPassword()
                )
        );
        User user = userRepository.findByMailUser(loginRequest.getMailUser()).orElseThrow();
        logger.info("Utilisateur avec l'email {} connecté avec succès", loginRequest.getMailUser());
        return userRepository.findByMailUser(loginRequest.getMailUser()).orElseThrow();
    }

    public User editUser(Long idUser, RegisterRequest userRequest) throws UserError {
        logger.info("Tentative de modification de l'utilisateur avec l'id : {}", idUser);
        Optional<User> optionalUser = userRepository.findById(idUser);
        if (optionalUser.isEmpty()) {
            logger.error("Échec de la modification : l'utilisateur avec l'id {} n'existe pas", idUser);
            throw new UserError("L'utilisateur à modifier n'existe pas");
        }


        User user = optionalUser.get();
        user.setMatriculeUser(userRequest.getMatriculeUser());
        user.setPrenomUser(userRequest.getPrenomUser());
        user.setNomUser(userRequest.getNomUser());
        user.setNumeroUser(userRequest.getNumeroUser());
        user.setMailUser(userRequest.getMailUser());
        user.setPassword(userRequest.getPassword());

        String hasPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hasPassword);

        user.setRole(Role.valueOf(userRequest.getRole()));

        logger.info("Utilisateur avec l'id {} modifié avec succès", idUser);
        return userRepository.save(user);
    }

    @Override
    public User getUser(Long idUser) throws UserError {
        logger.info("Tentative d'obtenir l'utilisateur avec l'id : {}\u001B", idUser);
        Optional<User> optionalUser = userRepository.findById(idUser);
        if (optionalUser.isEmpty()) {
            logger.error("Échec de l'obtention de l'utilisateur : l'utilisateur avec l'id {} n'existe pas", idUser);
            throw new UserError("L'utilisateur recherché n'existe pas");
        }
        return optionalUser.get();
    }

    @Override
    public List<User> listUsers() throws UserError {
        logger.info("Récupération de la liste de tous les utilisateurs");
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long idUser) throws UserError {
        logger.info("Tentative de suppression de l'utilisateur avec l'id : {}", idUser);
        Optional<User> optionalUser = userRepository.findById(idUser);
        if (optionalUser.isEmpty()) {
            logger.error("Échec de la suppression : l'utilisateur avec l'id {} n'existe pas", idUser);
            throw new UserError("L'utilisateur à supprimer n'existe pas");
        }
        userRepository.deleteById(idUser);
        logger.info("Utilisateur avec l'id {} supprimé avec succès", idUser);
    }
}
