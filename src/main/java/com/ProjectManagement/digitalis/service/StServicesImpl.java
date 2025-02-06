package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.dto.SousTacheRequest;
import com.ProjectManagement.digitalis.entitie.*;
import com.ProjectManagement.digitalis.repositorie.*;
import com.ProjectManagement.digitalis.dto.StUpdateRequest;
import com.ProjectManagement.digitalis.service.serviceIntreface.GtServices;
import com.ProjectManagement.digitalis.service.serviceIntreface.StServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StServicesImpl implements StServices {

    private final StRepository stRepository;
    private final GtRepository gtRepository;
    private final EvolutionRepository evolutionRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    private final GtServices gtServices;


    public StServicesImpl(StRepository stRepository, GtRepository gtRepository, EvolutionRepository evolutionRepository,
                          UserRepository userRepository, NotificationService notificationService,GtServices gtServices) {
        this.stRepository = stRepository;
        this.gtRepository = gtRepository;
        this.evolutionRepository = evolutionRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.gtServices = gtServices;
    }

    @Override
    public SousTache saveSousTache(SousTacheRequest sousTacheRequest) {
        log.info("Enregistrement d'une nouvelle sous-tâche : {}", sousTacheRequest.getTacheSt());

        SousTache sousTache = new SousTache();
        sousTache.setNumeroSt(sousTacheRequest.getNumeroSt());
        sousTache.setTacheSt(sousTacheRequest.getTacheSt());
        sousTache.setChargesSt(sousTacheRequest.getChargesSt());
        sousTache.setDateDeDebutSt(sousTacheRequest.getDateDeDebutSt());
        sousTache.setDateDeFinSt(sousTacheRequest.getDateDeFinSt());

        // Récupérer et associer les entités liées
        GrandeTache grandeTache = gtRepository.findById(sousTacheRequest.getIdGt())
                .orElseThrow(() -> new RuntimeException("Grande Tâche non trouvée"));


        /*Evolution evolution = evolutionRepository.findById(sousTacheRequest.getIdEvolution())
                .orElseThrow(() -> new RuntimeException("Évolution non trouvée"));*/
        User dev = userRepository.findById(sousTacheRequest.getDev())
                .orElseThrow(() -> new RuntimeException("dev non trouvé"));

        User testeur = userRepository.findById(sousTacheRequest.getTesteur())
                .orElseThrow(() -> new RuntimeException("testeur non trouvé"));

        sousTache.setGt(grandeTache);

        sousTache.setDev(dev);

        sousTache.setTesteur(testeur);
        sousTache.chargesStHeures(sousTacheRequest.getChargesSt());
        System.out.println(sousTache.getChargesStHeures());

        SousTache sousTacheSaved = stRepository.save(sousTache);

        if (grandeTache.getListSt() == null) {
            grandeTache.setListSt(new ArrayList<>());  // Initialiser la liste si elle est null
        }

        grandeTache.calculerChargesTotales();
        gtRepository.save(grandeTache);
        gtServices.updateGrandeTacheDates(grandeTache);
        String notificationMsg = "Une nouvelle tâche vous a été assignée : " + sousTache.getTacheSt();
        notificationService.createNotifcation(notificationMsg, dev.getIdUser());

        log.info("Sous-tâche enregistrée avec succès : {}", sousTache.getTacheSt());
        return sousTacheSaved;
    }

    @Override
    public Optional<SousTache> getSt(Long idSt) {
        log.info("Récupération de la sous-tâche avec l'ID : {}", idSt);

        return stRepository.findById(idSt);
    }

    @Override
    public SousTache editSt(Long idSt, StUpdateRequest st) {
        log.info("Mise à jour de la sous-tâche avec l'ID : {}", idSt);

        SousTache sousTache = stRepository.findById(idSt)
                .orElseThrow(() -> new RuntimeException("Sous-tâche non trouvée"));

        // Mise à jour des champs de la sous-tâche

        sousTache.setTacheSt(st.getTacheSt());
        sousTache.setChargesSt(st.getChargesSt());
        sousTache.setDateDeDebutSt(st.getDateDeDebutSt());
        sousTache.setDateDeFinSt(st.getDateDeFinSt());
        sousTache.setDateDeFinReelleSt(st.getDateDeFinReelleSt());

        sousTache.setRemarquesGt(st.getRemarquesGt());

        if(st.getEvolution() !=null){

            sousTache.setEvolution(st.getEvolution());
        }


        // Mise à jour des relations avec les entités liées
        if (st.getIdGt() != null) {
            GrandeTache grandeTache = gtRepository.findById(st.getIdGt())
                    .orElseThrow(() -> new RuntimeException("Grande Tâche non trouvée"));
            sousTache.setGt(grandeTache);
        }
        GrandeTache grandeTache = sousTache.getGt();


        if (st.getIdUser() != null) {
            User dev = userRepository.findById(st.getIdUser())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
            sousTache.setDev(dev);
        }

        SousTache sousTacheUpdated = stRepository.save(sousTache);
        grandeTache.calculerChargesTotales();
        gtServices.updateGrandeTacheDates(grandeTache);

        log.info("Sous-tâche mise à jour avec succès : {}", sousTacheUpdated.getEvolution());
        return sousTacheUpdated;
    }

    @Override
    public List<SousTache> listSt() {
        log.info("Récupération de la liste de toutes les sous-tâches");
        return stRepository.findAll();
    }

    @Override
    public void deleteSt(Long idSt) {
        log.info("Suppression de la sous-tâche avec l'ID : {}", idSt);

        if (stRepository.existsById(idSt)) {
            stRepository.deleteById(idSt);
            log.info("Sous-tâche supprimée avec succès : {}", idSt);
        } else {
            log.error("Aucune sous-tâche trouvée pour l'ID : {}", idSt);
            throw new RuntimeException("Aucune sous-tâche correspondante");
        }
    }


}
