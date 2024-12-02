package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.dto.SousTacheRequest;
import com.ProjectManagement.digitalis.entitie.*;
import com.ProjectManagement.digitalis.repositorie.*;
import com.ProjectManagement.digitalis.dto.StUpdateRequest;
import com.ProjectManagement.digitalis.service.serviceIntreface.StServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StServicesImpl implements StServices {


    private final StRepository stRepository;
    private final GtRepository gtRepository;
    private final EvolutionRepository evolutionRepository;
    private final UserRepository userRepository;

    public StServicesImpl(StRepository stRepository, GtRepository gtRepository, EvolutionRepository evolutionRepository, UserRepository userRepository) {
        this.stRepository = stRepository;
        this.gtRepository = gtRepository;
        this.evolutionRepository = evolutionRepository;
        this.userRepository = userRepository;
    }


    @Override
    public SousTache saveSousTache(SousTacheRequest sousTacheRequest) {
        SousTache sousTache = new SousTache();

        // Remplir les champs de la sous-tâche
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

        return stRepository.save(sousTache);
    }

    @Override
    public Optional<SousTache> getSt(Long idSt) {
        return stRepository.findById(idSt);
    }

    @Override
    public SousTache editSt(Long idSt, StUpdateRequest st) {
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

        return stRepository.save(sousTache);
    }


    @Override
    public List<SousTache> listSt() {
        return stRepository.findAll();
    }

    @Override
    public void deleteSt(Long idSt) {
        if(stRepository.existsById(idSt)){
            stRepository.deleteById(idSt);
        }else {

            throw new RuntimeException("aucune sous tache corespondantes");
        }

    }
}
