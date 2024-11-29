package com.ProjectManagement.digitalis.service.jwt;

import com.ProjectManagement.digitalis.entitie.User;
import com.ProjectManagement.digitalis.repositorie.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByMailUser(email)
                .orElseThrow(()->new UsernameNotFoundException("aucun utilisateur correspond a ce email: "+email));

        return new org.springframework.security.core.userdetails.User(user.getMailUser(), user.getPassword(), Collections.emptyList());

    }
}
