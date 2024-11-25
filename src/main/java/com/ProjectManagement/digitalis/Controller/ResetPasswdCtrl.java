package com.ProjectManagement.digitalis.Controller;

import com.ProjectManagement.digitalis.Services.PasswordRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/password-reset")
public class ResetPasswdCtrl {

    private final PasswordRestService passwordRestService;

    @PostMapping("/request")
    public ResponseEntity<String> requestPasswdReset(@RequestParam String email){

        passwordRestService.sendPasswrodResetToken(email);
        return ResponseEntity.ok("Vous allez recevoire un jeton de réinitialisation du mot de passe à votre adresse e-mail ");
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam String token,@RequestParam String newPasswd){
        passwordRestService.resetPassword(token,newPasswd);
        return ResponseEntity.ok("mot passe réinitialiser avec succès !");
    }
}
