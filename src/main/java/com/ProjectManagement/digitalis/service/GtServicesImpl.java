package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.dto.GrandeTacheRequest;
import com.ProjectManagement.digitalis.entitie.Evolution;
import com.ProjectManagement.digitalis.entitie.GrandeTache;
import com.ProjectManagement.digitalis.entitie.Projet;
import com.ProjectManagement.digitalis.repositorie.EvolutionRepository;
import com.ProjectManagement.digitalis.repositorie.GtRepository;
import com.ProjectManagement.digitalis.exception.GtError;
import com.ProjectManagement.digitalis.repositorie.ProjetRepository;
import com.ProjectManagement.digitalis.service.serviceIntreface.GtServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GtServicesImpl implements GtServices {

    private static final Logger logger = LoggerFactory.getLogger(GtServicesImpl.class);

    private final GtRepository gtRepository;
    private final ProjetRepository projetRepository;
    private final EvolutionRepository evolutionRepository;

    public GtServicesImpl(GtRepository gtRepository, ProjetRepository projetRepository,EvolutionRepository evolutionRepository) {
        this.gtRepository = gtRepository;
        this.projetRepository = projetRepository;
        this.evolutionRepository = evolutionRepository;


    }

    @Override
    public GrandeTache saveGt(GrandeTache gt) throws GtError {
        if (gt == null) {
            logger.error("Tentative d'enregistrement d'une grande tâche null");
            throw new GtError("La grande tache à enregistrer est null");
        }
        logger.info("Enregistrement de la grande tâche : {}", gt.getNomGt());
        GrandeTache savedGt = gtRepository.save(gt);
        logger.info("Grande tâche enregistrée avec succès : ");
        return savedGt;
    }

    @Override
    public GrandeTache editGt(Long idGt, GrandeTacheRequest gt) throws GtError {
        logger.info("Tentative de modification de la grande tâche avec l'ID : {}", idGt);
        Optional<GrandeTache> optionalGt = gtRepository.findById(idGt);
        if (optionalGt.isEmpty()) {
            logger.error("La grande tâche avec l'ID {} n'existe pas", idGt);
            throw new GtError("La grande tâche à modifier n'existe pas");
        }

        GrandeTache gt1 = optionalGt.get();

        gt1.setNomGt(gt.getNomGt());
        gt1.setChargesGt(gt.getChargesGt());
        gt1.setDateDeDebutGt(gt.getDateDeDebutGt());
        gt1.setDateDeFinGt(gt.getDateDeFinGt());

        if(gt.getEvolution() != null){
            gt1.setEvolution(gt.getEvolution());
        }


        if(gt.getProjet() != null){
            gt1.setProjet(gt.getProjet());
        }


        GrandeTache updatedGt = gtRepository.save(gt1);
        logger.info("Grande tâche modifiée avec succès : {}", updatedGt);
        return updatedGt;
    }

    @Override
    public GrandeTache getGt(Long idGt) throws GtError {
        logger.info("Tentative de récupération de la grande tâche avec l'ID : {}", idGt);
        Optional<GrandeTache> optionalGt = gtRepository.findById(idGt);
        if (optionalGt.isEmpty()) {
            logger.error("La grande tâche avec l'ID {} n'existe pas", idGt);
            throw new GtError("La grande tâche recherchée n'existe pas");
        }
        GrandeTache gt = optionalGt.get();
        logger.info("Grande tâche récupérée avec succès : {}", gt);
        return gt;
    }

    @Override
    public List<GrandeTache> listGt() throws GtError {
        logger.info("Récupération de la liste de toutes les grandes tâches");
        List<GrandeTache> gtList = gtRepository.findAll();
        logger.info("Nombre de grandes tâches récupérées : {}", gtList.size());
        return gtList;
    }

    @Override
    public void deleteGt(Long idGt) throws GtError {
        logger.info("Tentative de suppression de la grande tâche avec l'ID : {}", idGt);
        Optional<GrandeTache> optionalGt = gtRepository.findById(idGt);
        if (optionalGt.isEmpty()) {
            logger.error("La grande tâche avec l'ID {} n'existe pas", idGt);
            throw new GtError("La grande tâche à supprimer n'existe pas");
        }
        gtRepository.deleteById(idGt);
        logger.info("Grande tâche supprimée avec succès avec l'ID : {}", idGt);
    }


    @Override
    public List<GrandeTache> getGtByProjectId(Long projectId){
        Projet projet = projetRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException(""));
        return gtRepository.findByProjet(projet);

    }
}
