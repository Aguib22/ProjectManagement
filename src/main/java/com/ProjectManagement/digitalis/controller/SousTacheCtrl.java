package com.ProjectManagement.digitalis.controller;


import com.ProjectManagement.digitalis.dto.UpdateSurchargeAndObservation;
import com.ProjectManagement.digitalis.exception.ProjetError;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    private final TempsTravailService tempsTravailService;

    private final StServices stServices;

    public SousTacheCtrl(StServicesImpl stServicesImpl, GtRepository gtRepository, UserRepository userRepository, EvolutionRepository evolutionRepository, GtServices gtServices, EvolutionServices evolutionServices, UserServices userServices, TempsTravailService tempsTravailService, StServices stServices) {
        this.stServicesImpl = stServicesImpl;
        this.gtRepository = gtRepository;
        this.userRepository = userRepository;
        this.evolutionRepository = evolutionRepository;
        this.gtServices = gtServices;
        this.evolutionServices = evolutionServices;
        this.userServices = userServices;
        this.tempsTravailService = tempsTravailService;
        this.stServices = stServices;
    }


    @PostMapping(value = "/save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SousTache> saveSousTache(@RequestParam("tacheSt") String tacheSt,
                                                   @RequestParam("chargesSt") Float chargesSt,
                                                   @RequestParam("idGt") Long idGt,
                                                   @RequestParam("dateDeDebutSt") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateDeDebutSt,
                                                   @RequestParam("dateDeFinSt") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateDeFinSt,
                                                   @RequestParam("remarquesGt") String remarquesGt,
                                                   @RequestParam("dev") Long devId,
                                                   @RequestParam("testeur") Long testeurId,
                                                   @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        SousTacheRequest sousTache = new SousTacheRequest();
        sousTache.setTacheSt(tacheSt);
        sousTache.setChargesSt(chargesSt);
        sousTache.setIdGt(idGt);
        sousTache.setDateDeDebutSt(dateDeDebutSt);
        sousTache.setDateDeFinSt(dateDeFinSt);
        sousTache.setRemarquesGt(remarquesGt);
        sousTache.setDev(devId);
        sousTache.setTesteur(testeurId);
        String fileName = (file != null) ? file.getOriginalFilename() : null;
        SousTache sousTacheSaved = stServicesImpl.saveSousTache(sousTache, file, fileName);
        return ResponseEntity.ok(sousTacheSaved);
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
    public ResponseEntity<SousTache> updateSousTache(@PathVariable Long idSt, @RequestBody StUpdateRequest stUpdateRequest) throws ProjetError {

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

    @GetMapping("/get-stByGt/{idGt}")
    public ResponseEntity<List<SousTache>>getStByGrandeTache(@PathVariable Long idGt){
        try {
            List<SousTache> sousTaches = gtServices.getstByGt(idGt);
            return ResponseEntity.ok(sousTaches);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }

    @PostMapping("/worktimes/{idSt}")
    public ResponseEntity<List<TempsTravail>> addTempsTravail(@PathVariable Long idSt,@RequestBody TempsTravail tempsTravail){
        try {
            tempsTravailService.addTempsTravail(idSt,tempsTravail);
            List<TempsTravail> lstTmpWorkedBySt = tempsTravailService.getTmpWordked(idSt);
            return ResponseEntity.ok(lstTmpWorkedBySt);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/worktimes/get/{idSt}")
    public ResponseEntity<List<TempsTravail>> getTempsTravail(@PathVariable Long idSt){
        try {
            List<TempsTravail> lstTmpWorkedBySt = tempsTravailService.getTmpWordked(idSt);
            return ResponseEntity.ok(lstTmpWorkedBySt);
        }catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<SousTache> updateSurchargeAndObservation(
            @PathVariable Long id,
            @RequestBody UpdateSurchargeAndObservation dto) {

        SousTache updatedSousTache = stServicesImpl.updateSurchargeAndObservation(id, dto);
        return ResponseEntity.ok(updatedSousTache);
    }

    @GetMapping("/countStByStatus")
    public ResponseEntity<Map<String,Long>>countFilterSt(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
                                                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate){
        Map<String,Long> filterdSt = stServicesImpl.countStByStatus(startDate, endDate);
        return new ResponseEntity<>(filterdSt,HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/gt/{idGt}")
    public List<SousTache> getSousTachesByUserId(@PathVariable Long userId,@PathVariable Long idGt) {
        return stServices.getSousTachesByUserId(userId,idGt);
    }
}
