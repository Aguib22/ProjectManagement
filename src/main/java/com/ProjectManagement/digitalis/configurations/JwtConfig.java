package com.ProjectManagement.digitalis.configurations;

import com.ProjectManagement.digitalis.Services.jwt.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class JwtConfig {

    private final UserServiceImpl userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* Méthode privée pour définir un service de gestion des détails de l'utilisateur.
      `userService::loadUserByUsername` est une référence de méthode à la fonction `loadUserByUsername` dans `UserServiceImpl`,
       qui charge les détails de l'utilisateur à partir de la base de données via le nom d'utilisateur.*/
    private UserDetailsService userDetailsService() {
        return userService::loadUserByUsername;
    }

    /* Définition d'un bean pour l'AuthenticationManager.
      `AuthenticationManager` est responsable de la gestion de l'authentification des utilisateurs.
      `AuthenticationConfiguration` est utilisé pour obtenir une instance de `AuthenticationManager`.*/
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Définition d'un bean pour l'AuthenticationProvider, qui est responsable de la vérification des informations d'identification.
    @Bean
    AuthenticationProvider authenticationProvider() {
        // `DaoAuthenticationProvider` est une implémentation de l'interface `AuthenticationProvider` qui utilise un DAO pour récupérer les utilisateurs.
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        // Définit le `UserDetailsService` pour authentifier les utilisateurs via leur nom d'utilisateur.
        authProvider.setUserDetailsService(userDetailsService());

        // Définit l'encodeur de mot de passe qui sera utilisé pour vérifier les mots de passe hachés.
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
