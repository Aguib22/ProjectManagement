package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.entitie.User;
import com.ProjectManagement.digitalis.repositorie.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class PasswordRestService {

    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.resetPasswordUrl}")
    private String urlResetPasswd;

    public PasswordRestService(UserRepository userRepository, JavaMailSender javaMailSender, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
    }

    public void sendPasswrodResetToken(String email){
        log.info("Demande de réinitialisation du mot de passe pour l'email : {}", email);
        Optional<User> optionalUser = userRepository.findByMailUser(email);
        if(!optionalUser.isPresent()){
            log.error("Aucun utilisateur trouvé pour l'email : {}", email);
            throw new UsernameNotFoundException("Aucun utilisateur ne correspond au mail: "+ email);
        }

        String token = UUID.randomUUID().toString();
        User user = optionalUser.get();
        user.setPasswordResetToken(token);
        user.setExpirePasswordResetToken(LocalDateTime.now().plusMinutes(2));
        userRepository.save(user);

        String resetPasswdUrl =  urlResetPasswd + token;
        log.info("Token de réinitialisation généré pour l'utilisateur : {}", email);
        sendResetEmail(user.getMailUser(), resetPasswdUrl);
    }

    public void sendResetEmail(String email, String urlReset){
        log.info("Envoi de l'email de réinitialisation de mot de passe à : {}", email);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Récupération de mot de passe");
        message.setText("Cliquez sur ce lien ci-dessous pour modifier votre mot de passe : \n" + urlReset);
        javaMailSender.send(message);
        log.info("Email envoyé à : {}", email);
    }

    public void resetPassword(String token, String newPassword){
        log.info("Tentative de réinitialisation du mot de passe avec le token : {}", token);
        Optional<User> userOptional = userRepository.findByPasswordResetToken(token);
        if(!userOptional.isPresent()){
            log.error("Token invalide ou expiré : {}", token);
            throw new IllegalArgumentException("Ce token n'est plus valide!");
        }

        User user = userOptional.get();
        if(user.getExpirePasswordResetToken().isBefore(LocalDateTime.now())){
            log.error("Le token a expiré pour l'utilisateur : {}", user.getMailUser());
            throw new IllegalArgumentException("Le token a expiré !");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordResetToken(null);
        user.setExpirePasswordResetToken(null);
        userRepository.save(user);
        log.info("Mot de passe réinitialisé pour l'utilisateur : {}", user.getMailUser());
    }
}
