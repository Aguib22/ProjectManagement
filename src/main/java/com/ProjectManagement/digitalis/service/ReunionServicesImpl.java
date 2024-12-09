package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.entitie.Reunion;
import com.ProjectManagement.digitalis.exception.ReunionError;
import com.ProjectManagement.digitalis.repositorie.ReunionRepository;
import com.ProjectManagement.digitalis.service.serviceIntreface.ReunionServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ReunionServicesImpl implements ReunionServices {

    private final ReunionRepository reunionRepository;

    public ReunionServicesImpl(ReunionRepository reunionRepository) {
        this.reunionRepository = reunionRepository;
    }

    @Override
    public Reunion saveReunion(Reunion reunion) throws ReunionError {
        if (reunion == null) {
            log.error("Tentative d'enregistrer une réunion null");
            throw new ReunionError("La réunion à enregistrer n'existe pas");
        }
        log.info("Enregistrement de la réunion : {}", reunion.getOrdreDuJour());
        return reunionRepository.save(reunion);
    }

    @Override
    public Reunion getReunion(Long idReunion) throws ReunionError {
        Optional<Reunion> optionalReunion = reunionRepository.findById(idReunion);
        if (optionalReunion.isEmpty()) {
            log.error("La réunion recherchée avec l'ID {} n'existe pas", idReunion);
            throw new ReunionError("La réunion recherchée n'existe pas");
        }
        log.info("Récupération de la réunion : {}", optionalReunion.get().getOrdreDuJour());
        return optionalReunion.get();
    }

    @Override
    public Reunion editReunion(Long idReunion, Reunion reunion) throws ReunionError {
        Optional<Reunion> optionalReunion = reunionRepository.findById(idReunion);
        if (optionalReunion.isEmpty()) {
            log.error("La réunion avec l'ID {} à modifier n'existe pas", idReunion);
            throw new ReunionError("La réunion à modifier n'existe pas");
        }

        Reunion reunion1 = optionalReunion.get();
        reunion1.setDateHeureDebutReunion(reunion.getDateHeureDebutReunion());
        reunion1.setDateHeureFinReunion(reunion.getDateHeureFinReunion());
        reunion1.setOrdreDuJour(reunion.getOrdreDuJour());
        reunion1.setRemarquesR(reunion.getRemarquesR());
        reunion1.setListUser(reunion.getListUser());

        log.info("Modification de la réunion : {}", reunion1.getOrdreDuJour());
        return reunionRepository.save(reunion1);
    }

    @Override
    public List<Reunion> listReunion() {
        log.info("Récupération de la liste de toutes les réunions");
        return reunionRepository.findAll();
    }

    @Override
    public void deleteReunion(Long idReunion) throws ReunionError {
        Optional<Reunion> optionalReunion = reunionRepository.findById(idReunion);
        if (optionalReunion.isEmpty()) {
            log.error("La réunion avec l'ID {} à supprimer n'existe pas", idReunion);
            throw new ReunionError("La réunion à supprimer n'existe pas");
        }
        log.info("Suppression de la réunion avec l'ID : {}", idReunion);
        reunionRepository.deleteById(idReunion);
    }
}
