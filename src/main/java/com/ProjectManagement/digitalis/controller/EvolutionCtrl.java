package com.ProjectManagement.digitalis.controller;

import com.ProjectManagement.digitalis.entitie.Evolution;
import com.ProjectManagement.digitalis.service.EvolutionServicesImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/evolution")

public class EvolutionCtrl {

    private final EvolutionServicesImpl evolutionServices;

    public EvolutionCtrl(EvolutionServicesImpl evolutionServices) {
        this.evolutionServices = evolutionServices;
    }

    @PostMapping("/save")
    public Evolution saveEvolution(@RequestBody Evolution evolution){
        return evolutionServices.saveTraitement(evolution);
    }

    // Endpoint pour récupérer une évolution par ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Evolution> getEvolutionById(@PathVariable Long id) {
        Optional<Evolution> evolution = evolutionServices.getTraitement(id);
        return evolution.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint pour mettre à jour une évolution par ID
    @PutMapping("/edit/{id}")
    public ResponseEntity<Evolution> updateEvolution(@PathVariable Long id, @RequestBody Evolution evolution) {
        try {
            Evolution updatedEvolution = evolutionServices.editTraitement(id, evolution);
            return new ResponseEntity<>(updatedEvolution, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint pour lister toutes les évolutions
    @GetMapping("/get/liste")
    public ResponseEntity<List<Evolution>> getAllEvolutions() {
        List<Evolution> evolutions = evolutionServices.listTraitement();
        return new ResponseEntity<>(evolutions, HttpStatus.OK);
    }

    // Endpoint pour supprimer une évolution par ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEvolution(@PathVariable Long id) {
        try {
            evolutionServices.deleteTraitement(id);
            return new ResponseEntity<>("Evolution supprimée avec succès.", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la suppression de l'évolution.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
