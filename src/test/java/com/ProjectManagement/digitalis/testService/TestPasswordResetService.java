package com.ProjectManagement.digitalis.testService;


import com.ProjectManagement.digitalis.entitie.User;
import com.ProjectManagement.digitalis.repositorie.UserRepository;
import com.ProjectManagement.digitalis.service.PasswordRestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

/**
 * @author Aguibou sow
 * @date 2024-12-05 16:45
 * @package com.ProjectManagement.digitalis.testService
 * @project digitalis
 */


public class TestPasswordResetService {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PasswordRestService passwordRestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendPasswordResetTokenUserNotFound() {

        String email = "test@digitalis.com.gn";
        when(userRepository.findByMailUser(email)).thenReturn(Optional.empty());


        UsernameNotFoundException exception = Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            passwordRestService.sendPasswrodResetToken(email);
        });
        Assertions.assertEquals("aucun utilisateur ne correspond au mail: " + email, exception.getMessage());
        System.out.println(exception.getMessage());
    }

    @Test
    public void testSendPasswordResetTokenSuccess() {
        String email = "test@digitalis.com.gn";
        User user = new User();
        user.setMailUser(email);
        String token = UUID.randomUUID().toString();
        user.setPasswordResetToken(token);

        when(userRepository.findByMailUser(email)).thenReturn(Optional.of(user));

        // When
        passwordRestService.sendPasswrodResetToken(email);

        // Then
        Assertions.assertNotNull(user.getPasswordResetToken(), "Le token ne doit pas être null.");
        Assertions.assertNotNull(user.getExpirePasswordResetToken(), "La date d'expiration ne doit pas être null.");
        verify(userRepository, times(1)).save(user);

        // Vérification de l'email envoyé
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage message = messageCaptor.getValue();

        String messageText = message.getText();
        System.out.println("Message envoyé : " + messageText);


        Assertions.assertTrue(messageText.contains(user.getPasswordResetToken()));
    }

    @Test
    public void testResetPasswordTokenExpired() {
        // Given
        String token = UUID.randomUUID().toString();
        String newPassword = "newPassword123";
        User user = new User();
        user.setPasswordResetToken(token);
        user.setExpirePasswordResetToken(LocalDateTime.now().minusMinutes(1));
        when(userRepository.findByPasswordResetToken(token)).thenReturn(Optional.of(user));

        // When & Then
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            passwordRestService.resetPassword(token, newPassword);
        });
        Assertions.assertEquals("le token a expiré !", exception.getMessage());
    }

    @Test
    public void testResetPasswordSuccess() {

        String token = UUID.randomUUID().toString();
        String newPassword = "newPassword123";
        User user = new User();
        user.setPasswordResetToken(token);
        user.setExpirePasswordResetToken(LocalDateTime.now().plusMinutes(5));
        when(userRepository.findByPasswordResetToken(token)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(newPassword)).thenReturn("passwordEncoder");

        // When
        passwordRestService.resetPassword(token, newPassword);

        // Then
        Assertions.assertEquals("passwordEncoder", user.getPassword(), "Le mot de passe ne doit pas être encodé.");
        Assertions.assertNull(user.getPasswordResetToken());
        Assertions.assertNull(user.getExpirePasswordResetToken());
        verify(userRepository, times(1)).save(user);
    }
}

