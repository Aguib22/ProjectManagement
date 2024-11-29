package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.entitie.UserService;
import com.ProjectManagement.digitalis.repositorie.UserServiceRepository;
import org.apache.commons.lang3.RandomStringUtils;


import com.ProjectManagement.digitalis.entitie.Role;
import com.ProjectManagement.digitalis.entitie.User;
import com.ProjectManagement.digitalis.exception.UserError;
import com.ProjectManagement.digitalis.repositorie.ReunionRepository;
import com.ProjectManagement.digitalis.repositorie.UserRepository;
import com.ProjectManagement.digitalis.dto.LoginRequest;
import com.ProjectManagement.digitalis.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;
    private final UserServiceRepository userServiceRepository;
    @Autowired
    private final ReunionRepository reunionRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    @Override
    public User saveUser(User user) throws UserError {
        if (user == null) {
            throw new UserError("L'utilisateur à enregistrer est null");
        }
        return userRepository.save(user);
    }

    @Override
    public Boolean registUser(RegisterRequest registerRequest) {
        if ( userRepository.existsByMailUser(registerRequest.getMailUser())){
            return false;
        }
        User user = new User();
        UserService service = userServiceRepository.findById(registerRequest.getUserService())
                        .orElseThrow(()->new RuntimeException("le service indiqué n'existe pas"));


        BeanUtils.copyProperties(registerRequest,user);

        String password = RandomStringUtils.randomAlphabetic(8);
        registerRequest.setPassword(password);
        String hasPassword = passwordEncoder.encode(password);
        user.setUserService(service);
        user.setPassword(hasPassword);
        user.setRole(Role.valueOf(registerRequest.getRole()));

        userRepository.save(user);

        return true;
    }

    public User login(LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getMailUser(),
                        loginRequest.getPassword()
                )
        );
        return userRepository.findByMailUser(loginRequest.getMailUser()).orElseThrow();
    }

    public User editUser(Long idUser, RegisterRequest userRequest) throws UserError {
        Optional<User> optionalUser = userRepository.findById(idUser);
        if (optionalUser.isEmpty()) {
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




        return userRepository.save(user);
    }

    @Override
    public User getUser(Long idUser) throws UserError {
        Optional<User> optionalUser = userRepository.findById(idUser);
        if (optionalUser.isEmpty()) {
            throw new UserError("L'utilisateur recherché n'existe pas");
        }
        return optionalUser.get();
    }

    @Override
    public List<User> listUsers() throws UserError {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long idUser) throws UserError {
        Optional<User> optionalUser = userRepository.findById(idUser);
        if (optionalUser.isEmpty()) {
            throw new UserError("L'utilisateur à supprimer n'existe pas");
        }
        userRepository.deleteById(idUser);
    }
}
