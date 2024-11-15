package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.Controller.Request.UserRequest;
import com.ProjectManagement.digitalis.Entities.Reunion;
import com.ProjectManagement.digitalis.Entities.Role;
import com.ProjectManagement.digitalis.Entities.User;
import com.ProjectManagement.digitalis.Exception.UserError;
import com.ProjectManagement.digitalis.Repositories.ReunionRepository;
import com.ProjectManagement.digitalis.Repositories.UserRepository;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReunionRepository reunionRepository;

    @Override
    public User saveUser(User user) throws UserError {
        if (user == null) {
            throw new UserError("L'utilisateur à enregistrer est null");
        }
        return userRepository.save(user);
    }

    @Override
    public User editUser(Long idUser, UserRequest userRequest) throws UserError {
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
        user.setUpdatedAt(userRequest.getUpdatedAt());
        user.setRole(Role.valueOf(userRequest.getRole()));

        Reunion reunion = reunionRepository.findById(userRequest.getIdReunion()).get();
        user.setReunion(reunion);

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
