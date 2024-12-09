package com.ProjectManagement.digitalis.testService;

import com.ProjectManagement.digitalis.dto.LoginRequest;
import com.ProjectManagement.digitalis.dto.RegisterRequest;
import com.ProjectManagement.digitalis.entitie.User;
import com.ProjectManagement.digitalis.entitie.UserService;
import com.ProjectManagement.digitalis.repositorie.UserRepository;
import com.ProjectManagement.digitalis.repositorie.UserServiceRepository;
import com.ProjectManagement.digitalis.service.UserServicesImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Aguibou sow
 * @date 2024-12-06 11:33
 * @package com.ProjectManagement.digitalis.testService
 * @project digitalis
 */


public class TestUserServiceImpl {

    @InjectMocks
    private UserServicesImpl userServices; // Classe à tester

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserServiceRepository userServiceRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegistUserSuccess() {
        // Given
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setMailUser("test@digitalis.com.gn");
        registerRequest.setUserService(1L);
        registerRequest.setRole("ADMIN");

        UserService service = new UserService();
        service.setIdUserService(1L);
        service.setNomUserService("IT Department");

        when(userRepository.existsByMailUser(registerRequest.getMailUser())).thenReturn(false);
        when(userServiceRepository.findById(registerRequest.getUserService())).thenReturn(Optional.of(service));
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");

        // When
        Boolean result = userServices.registUser(registerRequest);

        // Then
        Assertions.assertTrue(result, "L'inscription réussie.");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testRegistUserFailed(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setMailUser("test@digitalis.com.gn");
        registerRequest.setUserService(1L);
        registerRequest.setRole("ADMIN");

        when(userRepository.existsByMailUser(registerRequest.getMailUser())).thenReturn(true);

        Boolean result = userServices.registUser(registerRequest);

        Assertions.assertFalse(result,"Erreur car le user existe déjà !");
        verify(userRepository,never()).save(any(User.class));
        verify(userServiceRepository,never()).findById(anyLong());

    }


    @Test
    public void testLoginSuccess() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setMailUser("test@digitalis.com.gn");
        loginRequest.setPassword("password");

        User user = new User();
        user.setMailUser("test@digitalis.com.gn");
        // When
        when(userRepository.findByMailUser(loginRequest.getMailUser())).thenReturn(Optional.of(user));
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        User loggedUser = userServices.login(loginRequest);

        // Then
        Assertions.assertEquals(user, loggedUser);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void testLogninFailed(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setMailUser("test@digitalis.com.gn");
        loginRequest.setPassword("password");

        when(userRepository.findByMailUser(loginRequest.getMailUser())).thenReturn(Optional.empty());
        lenient().when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Erreur de connexion"));

        Assertions.assertThrows(BadCredentialsException.class, ()->{
            userServices.login(loginRequest);
                });

        verify(authenticationManager,times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

}
