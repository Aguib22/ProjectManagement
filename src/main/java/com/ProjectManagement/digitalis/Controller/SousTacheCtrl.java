package com.ProjectManagement.digitalis.Controller;


import com.ProjectManagement.digitalis.Repositories.EvolutionRepository;
import com.ProjectManagement.digitalis.Repositories.GtRepository;
import com.ProjectManagement.digitalis.Repositories.UserRepository;
import com.ProjectManagement.digitalis.dto.SousTacheRequest;
import com.ProjectManagement.digitalis.Entities.*;
import com.ProjectManagement.digitalis.Services.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/sous-tache")
public class  SousTacheCtrl {
    @Autowired
    private final StServicesImpl stServicesImpl;

    private final GtRepository gtRepository;
    private final UserRepository userRepository;
    private final EvolutionRepository evolutionRepository;
    @Autowired
    private GtServices gtServices;
    @Autowired
    private EvolutionServices evolutionServices;
    @Autowired
    private UserServices userServices;
    @Autowired
    private StServices stServices;



    @PostMapping("/save")
    public ResponseEntity<?> createSousTache(@RequestBody SousTacheRequest sousTacheRequest) {
        try {

            SousTache sousTache = new SousTache();

            sousTache.setNumeroSt(sousTacheRequest.getNumeroSt());
            sousTache.setTacheSt(sousTacheRequest.getTacheSt());
            sousTache.setChargesSt(sousTacheRequest.getChargesSt());
            sousTache.setDateDeDebutSt(sousTacheRequest.getDateDeDebutSt());
            sousTache.setDateDeFinSt(sousTacheRequest.getDateDeFinSt());


            sousTache.setRemarquesGt(sousTacheRequest.getRemarquesGt());


            sousTache.setGt(gtRepository.findById(sousTacheRequest.getIdGt())
                    .orElseThrow(() -> new RuntimeException("GrandeTache introuvable")));
            sousTache.setTraitement(evolutionRepository.findById(sousTacheRequest.getIdEvolution())
                    .orElseThrow(() -> new RuntimeException("Evolution introuvable")));
            sousTache.setUser(userRepository.findById(sousTacheRequest.getIdUser())
                    .orElseThrow(() -> new RuntimeException("Utilisateur introuvable")));


            SousTache savedSousTache = stServicesImpl.saveSt(sousTache);


            return new ResponseEntity<>(savedSousTache, HttpStatus.CREATED);
        } catch (RuntimeException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /*@PostMapping("/save")
    public SousTache sousTacheSave(@RequestBody SousTacheRequest st) throws GtError, UserError {

        SousTache st1 = new SousTache();

        st1.setNumeroSt(st.getNumeroSt());
        st1.setTacheSt(st.getTacheSt());
        st1.setChargesSt(st.getChargesSt());
        st1.setDateDeDebutSt(st.getDateDeDebutSt());
        st1.setDateDeFinSt(st.getDateDeFinSt());
        st1.setDateDeFinReelleSt(st.getDateDeFinReelleSt());
        st1.setEvolutionSt(st.getEvolutionSt());
        st1.setSurchargesGt(st.getSurchargesGt());
        st1.setRemarquesGt(st.getRemarquesGt());

        GrandeTache grandeTache = gtServices.getGt(st.getGt());
        st1.setGt(grandeTache);

        Evolution evolution = evolutionServices.getTraitement(st.getEvolution()).get();
        st1.setTraitement(evolution);

        User user = userServices.getUser(st.getUser());
        st1.setUser(user);


        return stServices.saveSt(st1);
    }*/

    // Endpoint pour récupérer une sous-tâche par son ID
    @GetMapping("/get/{idSt}")
    public ResponseEntity<SousTache> getSousTache(@PathVariable Long idSt) {
        Optional<SousTache> sousTacheOptional = stServicesImpl.getSt(idSt);
        return sousTacheOptional.map(sousTache -> new ResponseEntity<>(sousTache, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint pour modifier une sous-tâche existante
    @PutMapping("/edit/{idSt}")
    public ResponseEntity<SousTache> updateSousTache(@PathVariable Long idSt, @RequestBody SousTacheRequest sousTache) {
        try {
            SousTache updatedSousTache = stServicesImpl.editSt(idSt, sousTache);
            return new ResponseEntity<>(updatedSousTache, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint pour récupérer la liste de toutes les sous-tâches
    @GetMapping
    public ResponseEntity<List<SousTache>> getAllSousTaches() {
        List<SousTache> sousTaches = stServicesImpl.listSt();
        return new ResponseEntity<>(sousTaches, HttpStatus.OK);
    }

    // Endpoint pour supprimer une sous-tâche par son ID
    @DeleteMapping("/del/{idSt}")
    public ResponseEntity<String> deleteSousTache(@PathVariable Long idSt) {
        try {
            stServicesImpl.deleteSt(idSt);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
