package com.ProjectManagement.digitalis.controller;


import com.ProjectManagement.digitalis.repositorie.EvolutionRepository;
import com.ProjectManagement.digitalis.repositorie.GtRepository;
import com.ProjectManagement.digitalis.repositorie.UserRepository;
import com.ProjectManagement.digitalis.dto.SousTacheRequest;
import com.ProjectManagement.digitalis.entitie.*;
import com.ProjectManagement.digitalis.service.*;
import com.ProjectManagement.digitalis.dto.StUpdateRequest;
import com.ProjectManagement.digitalis.service.serviceIntreface.EvolutionServices;
import com.ProjectManagement.digitalis.service.serviceIntreface.GtServices;
import com.ProjectManagement.digitalis.service.serviceIntreface.StServices;
import com.ProjectManagement.digitalis.service.serviceIntreface.UserServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/sous-tache")
public class  SousTacheCtrl {
    private final StServicesImpl stServicesImpl;
    private final GtRepository gtRepository;
    private final UserRepository userRepository;
    private final EvolutionRepository evolutionRepository;
    private final GtServices gtServices;

    private final EvolutionServices evolutionServices;
    private final UserServices userServices;

    private final StServices stServices;

    public SousTacheCtrl(StServicesImpl stServicesImpl, GtRepository gtRepository, UserRepository userRepository, EvolutionRepository evolutionRepository, GtServices gtServices, EvolutionServices evolutionServices, UserServices userServices, StServices stServices) {
        this.stServicesImpl = stServicesImpl;
        this.gtRepository = gtRepository;
        this.userRepository = userRepository;
        this.evolutionRepository = evolutionRepository;
        this.gtServices = gtServices;
        this.evolutionServices = evolutionServices;
        this.userServices = userServices;
        this.stServices = stServices;
    }


    @PostMapping("/save")
    public ResponseEntity<SousTache> saveSousTache(@RequestBody SousTacheRequest sousTacheRequest) {
        SousTache sousTache = stServicesImpl.saveSousTache(sousTacheRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(sousTache);
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
    public ResponseEntity<SousTache> updateSousTache(@PathVariable Long idSt, @RequestBody StUpdateRequest stUpdateRequest) {

        SousTache sousTache = stServicesImpl.editSt(idSt,stUpdateRequest);
        return ResponseEntity.ok(sousTache);


    }

    // Endpoint pour récupérer la liste de toutes les sous-tâches
    @GetMapping("/get/liste")
    public ResponseEntity<List<SousTache>> getAllSousTaches() {
        List<SousTache> sousTaches = stServicesImpl.listSt();
        return new ResponseEntity<>(sousTaches, HttpStatus.OK);
    }

    // Endpoint pour supprimer une sous-tâche par son ID
    @DeleteMapping("/delete/{idSt}")
    public ResponseEntity<String> deleteSousTache(@PathVariable Long idSt) {
        try {
            stServicesImpl.deleteSt(idSt);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
