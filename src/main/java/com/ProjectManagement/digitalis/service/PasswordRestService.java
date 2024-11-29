package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.entitie.User;
import com.ProjectManagement.digitalis.repositorie.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordRestService {
    @Autowired
    private final UserRepository userRepository;

    private final JavaMailSender javaMailSender;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Value("${app.resetPasswordUrl}")
    private String urlResetPasswd;

    public void sendPasswrodResetToken(String email){
        Optional<User> optionalUser = userRepository.findByMailUser(email);
        if(!optionalUser.isPresent()){
            throw new UsernameNotFoundException("aucun utilisateur ne correspond au mail: "+ email);
        }


        String token = UUID.randomUUID().toString();
        User user = optionalUser.get();
        user.setPasswordResetToken(token);
        user.setExpirePasswordResetToken(LocalDateTime.now().plusMinutes(2));
        userRepository.save(user);
        String resetPasswdUrl =  urlResetPasswd+token;
        sendResetEmail(user.getMailUser(),resetPasswdUrl);
    }

    public void sendResetEmail(String email, String urlReset){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Recupération de mot de passe");
        message.setText("Cliquer sur ce lien ci-dessous pour modifier votre mot de passe: \n" + urlReset);
        javaMailSender.send(message);
    }

    public void  resetPassword(String token,String newPassword){
        Optional<User> userOptional = userRepository.findByPasswordResetToken(token);
        if(!userOptional.isPresent()){
            throw new IllegalArgumentException("ce token n'est plus valide!");
        }
        User user = userOptional.get();
        if(user.getExpirePasswordResetToken().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("le token a expiré !");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordResetToken(null);
        user.setExpirePasswordResetToken(null);
        userRepository.save(user);
    }


}
