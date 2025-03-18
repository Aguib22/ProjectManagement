package com.ProjectManagement.digitalis.controller;

import com.ProjectManagement.digitalis.dto.NewProjetDto;
import com.ProjectManagement.digitalis.dto.ProjectDto;
import com.ProjectManagement.digitalis.entitie.Evolution;
import com.ProjectManagement.digitalis.entitie.ProjectUser;
import com.ProjectManagement.digitalis.entitie.Projet;
import com.ProjectManagement.digitalis.entitie.User;
import com.ProjectManagement.digitalis.exception.ProjetError;
import com.ProjectManagement.digitalis.service.serviceIntreface.ProjetServices;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projet")

public class ApiProjetController {


    private final ProjetServices projetServices;

    public ApiProjetController(ProjetServices projetServices) {
        this.projetServices = projetServices;
    }


    @PostMapping(value = "/save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProjet(
            @RequestParam("nomProjet") String nomProjet,
            @RequestParam   ("descProjet") String descProjet,
            @RequestParam("dateDebutProjet") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateDebutProjet,
            @RequestParam("dateFinProject") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateFinProject,
            @RequestParam("file") MultipartFile fichierSpec,
            @RequestParam("fileName") String fileName,
            @RequestParam("users") String usersJson) {

        try {
            // Création de l'objet Projet
            Projet projet = new Projet();
            projet.setNomProjet(nomProjet);
            projet.setDescProjet(descProjet);
            projet.setDateDebutProjet(dateDebutProjet);
            projet.setDateFinProject(dateFinProject);
            Evolution evolution = new Evolution();
            evolution.setEvolution("initiale");
            evolution.setIdTraitement(3L);
            projet.setEvolution(evolution);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<User> users;
            try {
                users = objectMapper.readValue(usersJson, new TypeReference<List<User>>() {});
            } catch (IOException e) {
                return ResponseEntity.badRequest().body("Erreur lors de la désérialisation des utilisateurs");
            }
            List<ProjectUser> projectUsers = users.stream().map(user ->
                    ProjectUser.builder()
                            .user(user)
                            .projet(projet)
                            .enabled(true)
                            .createdAt(LocalDateTime.now())
                            .build()
            ).collect(Collectors.toList());
           projet.setProjectUsers(projectUsers);

            // Enregistrer le projet et le fichier
            Projet savedProjet = projetServices.saveProjet(projet, fichierSpec,fileName);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedProjet);
        } catch (ProjetError | IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get/{idProjet}")
    public Projet getProjet(@PathVariable Long idProjet) throws ProjetError{
        return projetServices.getProjet(idProjet);
    }

    @GetMapping("get/liste")
    public List<Projet> listProjet(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
                                   @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate){
        return projetServices.listProjet(startDate,endDate);
    }

    @DeleteMapping("delete/{idProjet}")
    public void deleteProjet(@PathVariable Long idProjet) throws ProjetError{
        projetServices.deleteProjet(idProjet);
    }

    @PutMapping("/edit/{idProjet}")
    public void editProjet(@PathVariable Long idProjet, @RequestBody ProjectDto projet) throws ProjetError{
        projetServices.editProjet(idProjet, projet);
    }

    @GetMapping("/{idProjet}/user")
    public ResponseEntity<List<User>> getUserByProjet(@PathVariable Long idProjet){
        List<User>users = projetServices.getUsersByProjetId(idProjet);

        return ResponseEntity.ok(users);
    }

    @GetMapping("/projets/{userId}")
    public List<Projet> getProjetsByUserId(@PathVariable Long userId) {
        return projetServices.getProjetsByUserId(userId);
    }

}
