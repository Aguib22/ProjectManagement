package com.ProjectManagement.digitalis.controller;

import com.ProjectManagement.digitalis.dto.ChangePwdDefaultRequest;
import com.ProjectManagement.digitalis.service.PasswordRestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/password-reset")
public class ResetPasswdCtrl {

    private final PasswordRestService passwordRestService;

    public ResetPasswdCtrl(PasswordRestService passwordRestService) {
        this.passwordRestService = passwordRestService;
    }

    @PutMapping("/request")
    public ResponseEntity<String> requestPasswdReset(@RequestParam String email){

        passwordRestService.sendPasswrodResetToken(email);
        return ResponseEntity.ok("Vous allez recevoire un jeton de réinitialisation du mot de passe à votre adresse e-mail ");
    }



    @PutMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam String token,@RequestParam String newPasswd){
        passwordRestService.resetPassword(token,newPasswd);
        return ResponseEntity.ok("mot passe réinitialiser avec succès !");
    }

    @PutMapping("/change-password")
    public ResponseEntity<Map<String, String>> resetDefaultPwd(@RequestBody ChangePwdDefaultRequest changePwdDefaultRequest){
        passwordRestService.changePasswdDefault(changePwdDefaultRequest);
        Map<String, String> response = new HashMap<>();
        response.put("message", "le mot de passe par defaut à été modifié.");
        return ResponseEntity.ok(response);

    }
}
