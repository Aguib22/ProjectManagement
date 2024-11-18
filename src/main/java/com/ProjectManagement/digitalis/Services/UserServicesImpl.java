package com.ProjectManagement.digitalis.Services;

import org.apache.commons.lang3.RandomStringUtils;


import com.ProjectManagement.digitalis.Entities.Role;
import com.ProjectManagement.digitalis.Entities.User;
import com.ProjectManagement.digitalis.Exception.UserError;
import com.ProjectManagement.digitalis.Repositories.ReunionRepository;
import com.ProjectManagement.digitalis.Repositories.UserRepository;
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

    @Autowired
    private ReunionRepository reunionRepository;

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
        BeanUtils.copyProperties(registerRequest,user);

        String password = RandomStringUtils.randomAlphabetic(8);
        registerRequest.setPassword(password);
        String hasPassword = passwordEncoder.encode(password);

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
        user.setCreatedAt(user.getCreatedAt());

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
