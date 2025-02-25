package com.ProjectManagement.digitalis.controller;

import com.ProjectManagement.digitalis.dto.ProjectDto;
import com.ProjectManagement.digitalis.entitie.Projet;
import com.ProjectManagement.digitalis.exception.ProjetError;
import com.ProjectManagement.digitalis.service.serviceIntreface.ProjetServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
            @RequestParam("fileName") String fileName) {

        try {
            // Création de l'objet Projet
            Projet projet = new Projet();
            projet.setNomProjet(nomProjet);
            projet.setDescProjet(descProjet);
            projet.setDateDebutProjet(dateDebutProjet);
            projet.setDateFinProject(dateFinProject);

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
    public Projet editProjet(@PathVariable Long idProjet, @RequestBody ProjectDto projet) throws ProjetError{
        return projetServices.editProjet(idProjet, projet);
    }


}
