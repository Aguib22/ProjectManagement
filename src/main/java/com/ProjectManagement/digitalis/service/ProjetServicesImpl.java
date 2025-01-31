package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.dto.ProjectDto;
import com.ProjectManagement.digitalis.entitie.Evolution;
import com.ProjectManagement.digitalis.entitie.Projet;
import com.ProjectManagement.digitalis.exception.ProjetError;
import com.ProjectManagement.digitalis.repositorie.EvolutionRepository;
import com.ProjectManagement.digitalis.repositorie.ProjetRepository;
import com.ProjectManagement.digitalis.service.serviceIntreface.ProjetServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProjetServicesImpl implements ProjetServices {

    private final ProjetRepository projetRepository;

    private final EvolutionRepository evolutionRepository;
    public ProjetServicesImpl(ProjetRepository projetRepository,EvolutionRepository evolutionRepository) {
        this.projetRepository = projetRepository;
        this.evolutionRepository = evolutionRepository;
    }

    @Override
    public Projet saveProjet(Projet projet) throws ProjetError {
        if (projet == null) {
            log.error("Tentative d'enregistrer un projet null");
            throw new ProjetError("Le Projet à enregistré est null");
        }
        log.info("Enregistrement du projet : {}", projet.getNomProjet());
        return projetRepository.save(projet);
    }

    @Override
    public Projet editProjet(Long idProjet, ProjectDto projet) throws ProjetError {
        Optional<Projet> optionalProjet = projetRepository.findById(idProjet);
        if (optionalProjet.isEmpty()) {
            log.error("Le projet avec l'ID {} n'existe pas", idProjet);
            throw new ProjetError("Le projet à modifier n'existe pas");
        }

        Projet projet1 = optionalProjet.get();
        projet1.setDescProjet(projet.getDescProjet());
        projet1.setNomProjet(projet.getNomProjet());
        projet1.setDateDebutProjet(projet.getDateDebutProjet());
        projet1.setDateFinProject(projet.getDateFinProject());

        if(projet.getEvolution() != null){
            projet1.setEvolution(projet.getEvolution());
        }


        log.info("Modification du projet : {}", projet1.getNomProjet());
        return projetRepository.save(projet1);
    }

    @Override
    public Projet getProjet(Long idProjet) throws ProjetError {
        Optional<Projet> optionalProjet = projetRepository.findById(idProjet);
        if (optionalProjet.isEmpty()) {
            log.error("Le projet recherché avec l'ID {} n'existe pas", idProjet);
            throw new ProjetError("Le projet rechercher n'existe pas");
        }
        log.info("Récupération du projet : {}", optionalProjet.get().getNomProjet());
        return optionalProjet.get();
    }

    @Override
    public List<Projet> listProjet() {
        log.info("Récupération de la liste de tous les projets");
        return projetRepository.findAll();
    }

    @Override
    public void deleteProjet(Long idProjet) throws ProjetError {
        Optional<Projet> optionalProjet = projetRepository.findById(idProjet);
        if (optionalProjet.isEmpty()) {
            log.error("Le projet avec l'ID {} à supprimer n'existe pas", idProjet);
            throw new ProjetError("Le projet à supprimer n'existe pas");
        }
        log.info("Suppression du projet avec l'ID : {}", idProjet);
        projetRepository.deleteById(idProjet);
    }
}
