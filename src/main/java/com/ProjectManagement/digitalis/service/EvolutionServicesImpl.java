package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.entitie.Evolution;
import com.ProjectManagement.digitalis.repositorie.EvolutionRepository;
import com.ProjectManagement.digitalis.service.serviceIntreface.EvolutionServices;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvolutionServicesImpl implements EvolutionServices {

    private static final Logger logger = LoggerFactory.getLogger(EvolutionServicesImpl.class);

    private final EvolutionRepository evolutionServices;

    public EvolutionServicesImpl(EvolutionRepository evolutionServices) {
        this.evolutionServices = evolutionServices;
    }

    @Override
    public Evolution saveTraitement(Evolution traitement) {
        logger.info("Tentative d'enregistrement de l'évolution : {}", traitement.getEvolution());
        Evolution savedEvolution = evolutionServices.save(traitement);
        logger.info("Évolution enregistrée avec succès : {}", savedEvolution);
        return savedEvolution;
    }

    @Override
    public Optional<Evolution> getTraitement(Long idTraitement) {
        logger.info("Tentative de récupération de l'évolution avec l'ID : {}", idTraitement);
        Optional<Evolution> evolution = evolutionServices.findById(idTraitement);
        if (evolution.isPresent()) {
            logger.info("Évolution trouvée : {}", evolution.get());
        } else {
            logger.warn("Aucune évolution trouvée pour l'ID : {}", idTraitement);
        }
        return evolution;
    }

    @Override
    public Evolution editTraitement(Long idTraitement, Evolution traitement) {
        logger.info("Tentative de modification de l'évolution avec l'ID : {}", idTraitement);
        return evolutionServices.findById(idTraitement)
                .map(evolution -> {
                    evolution.setEvolution(traitement.getEvolution());
                    logger.info("Évolution modifiée avec succès : {}", evolution);
                    return evolutionServices.save(evolution);
                })
                .orElseThrow(() -> {
                    logger.error("Évolution avec l'ID {} non trouvée", idTraitement);
                    return new RuntimeException("Evolution not found");
                });
    }

    @Override
    public List<Evolution> listTraitement() {
        logger.info("Récupération de la liste de toutes les évolutions");
        return evolutionServices.findAll();
    }

    @Override
    public void deleteTraitement(Long idTraitement) {
        logger.info("Tentative de suppression de l'évolution avec l'ID : {}", idTraitement);
        evolutionServices.deleteById(idTraitement);
        logger.info("Suppression de l'évolution effectuée avec succès pour l'ID : {}", idTraitement);
    }
}
