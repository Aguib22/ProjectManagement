package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.dto.SousTacheRequest;
import com.ProjectManagement.digitalis.entitie.*;
import com.ProjectManagement.digitalis.repositorie.*;
import com.ProjectManagement.digitalis.dto.StUpdateRequest;
import com.ProjectManagement.digitalis.service.serviceIntreface.StServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public StServicesImpl(StRepository stRepository, GtRepository gtRepository, EvolutionRepository evolutionRepository, UserRepository userRepository, NotificationService notificationService) {
        this.stRepository = stRepository;
        this.gtRepository = gtRepository;
        this.evolutionRepository = evolutionRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
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
        Evolution evolution = evolutionRepository.findById(sousTacheRequest.getIdEvolution())
                .orElseThrow(() -> new RuntimeException("Évolution non trouvée"));
        User user = userRepository.findById(sousTacheRequest.getIdUser())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        sousTache.setGt(grandeTache);
        sousTache.setTraitement(evolution);
        sousTache.setUser(user);

        SousTache sousTacheSaved = stRepository.save(sousTache);

        String notificationMsg = "Une nouvelle tâche vous a été assignée : " + sousTache.getTacheSt();
        notificationService.createNotifcation(notificationMsg, user.getIdUser());

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
        sousTache.setNumeroSt(st.getNumeroSt());
        sousTache.setTacheSt(st.getTacheSt());
        sousTache.setChargesSt(st.getChargesSt());
        sousTache.setDateDeDebutSt(st.getDateDeDebutSt());
        sousTache.setDateDeFinSt(st.getDateDeFinSt());
        sousTache.setEvolutionSt(st.getEvolutionSt());
        sousTache.setDateDeFinReelleSt(st.getDateDeFinReelleSt());
        sousTache.setSurchargesGt(st.getSurchargesGt());
        sousTache.setRemarquesGt(st.getRemarquesGt());

        // Mise à jour des relations avec les entités liées
        if (st.getIdGt() != null) {
            GrandeTache grandeTache = gtRepository.findById(st.getIdGt())
                    .orElseThrow(() -> new RuntimeException("Grande Tâche non trouvée"));
            sousTache.setGt(grandeTache);
        }

        if (st.getIdEvolution() != null) {
            Evolution evolution = evolutionRepository.findById(st.getIdEvolution())
                    .orElseThrow(() -> new RuntimeException("Évolution non trouvée"));
            sousTache.setTraitement(evolution);
        }

        if (st.getIdUser() != null) {
            User user = userRepository.findById(st.getIdUser())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
            sousTache.setUser(user);
        }

        SousTache sousTacheUpdated = stRepository.save(sousTache);

        log.info("Sous-tâche mise à jour avec succès : {}", sousTacheUpdated.getTacheSt());
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
