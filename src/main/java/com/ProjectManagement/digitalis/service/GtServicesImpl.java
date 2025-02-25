package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.dto.GrandeTacheRequest;
import com.ProjectManagement.digitalis.entitie.GrandeTache;
import com.ProjectManagement.digitalis.entitie.Projet;
import com.ProjectManagement.digitalis.entitie.SousTache;
import com.ProjectManagement.digitalis.exception.ProjetError;
import com.ProjectManagement.digitalis.repositorie.EvolutionRepository;
import com.ProjectManagement.digitalis.repositorie.GtRepository;
import com.ProjectManagement.digitalis.exception.GtError;
import com.ProjectManagement.digitalis.repositorie.ProjetRepository;
import com.ProjectManagement.digitalis.repositorie.StRepository;
import com.ProjectManagement.digitalis.service.serviceIntreface.GtServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GtServicesImpl implements GtServices {

    private static final Logger logger = LoggerFactory.getLogger(GtServicesImpl.class);

    private final GtRepository gtRepository;
    private final ProjetRepository projetRepository;
    private final EvolutionRepository evolutionRepository;
    private final ProjetServicesImpl projetServices;
    private final StRepository stRepository;

    public GtServicesImpl(GtRepository gtRepository, ProjetRepository projetRepository, EvolutionRepository evolutionRepository, ProjetServicesImpl projetServices, StRepository stRepository) {
        this.gtRepository = gtRepository;
        this.projetRepository = projetRepository;
        this.evolutionRepository = evolutionRepository;


        this.projetServices = projetServices;
        this.stRepository = stRepository;
    }

    @Override
    public GrandeTache saveGt(GrandeTache gt) throws GtError {
        if (gt == null) {
            logger.error("Tentative d'enregistrement d'une grande tâche null");
            throw new GtError("La grande tache à enregistrer est null");
        }
        logger.info("Enregistrement de la grande tâche : {}", gt.getNomGt());
        Projet projet = gt.getProjet();
        GrandeTache savedGt = gtRepository.save(gt);
        projetServices.updateProjetDates(projet);
        logger.info("Grande tâche enregistrée avec succès : ");

        //System.out.println("liste Gt: "+ savedGt.getProjet().getListGt());
        return savedGt;
    }

    @Override
    public GrandeTache editGt(Long idGt, GrandeTacheRequest gt) throws GtError, ProjetError {
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


        projetServices.updateProjetDates(updatedGt.getProjet());
        //System.out.println("liste Gt: "+ updatedGt.getProjet().getListGt());
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
        GrandeTache gt = optionalGt.get();

        List<SousTache> stByGt = getstByGt(idGt);
        stByGt.forEach(sousTache -> stRepository.deleteById(sousTache.getIdSt()));

        logger.info("Avant suppression : Liste des GrandeTaches du projet : {}", optionalGt.get().getProjet().getListGt());

        Projet projet = gt.getProjet();
        projet.getListGt().remove(gt);
        projetRepository.save(projet);
        gtRepository.deleteById(idGt);

        logger.info("après suppression : Liste des GrandeTaches du projet : {}", optionalGt.get().getProjet().getListGt());
        projetServices.updateProjetDates(gt.getProjet());
        logger.info("Grande tâche supprimée avec succès avec l'ID : {}", idGt);
    }

    @Override
    public List<SousTache> getstByGt(Long gtId){
        GrandeTache grandeTache = gtRepository.findById(gtId)
                .orElseThrow();
        return stRepository.findByGt(grandeTache);
    }


    @Override
    public void updateGrandeTacheDates(GrandeTache grandeTache) {
        List<SousTache> lstSt = this.getstByGt(grandeTache.getIdGt());
        if (grandeTache.getListSt() == null) {
            grandeTache.setListSt(new ArrayList<>());
        } else {
            grandeTache.getListSt().clear();
        }
        grandeTache.getListSt().addAll(lstSt);

        // Trouver la date de début la plus récente
        Date dateDebutMin = lstSt.stream()
                .map(SousTache::getDateDeDebutSt)
                .filter(Objects::nonNull)
                .min(Date::compareTo)
                .orElse(null);

        // Trouver la date de fin la plus ancienne
        Date dateFinMax = lstSt.stream()
                .map(SousTache::getDateDeFinSt)
                .filter(Objects::nonNull)
                .max(Date::compareTo)
                .orElse(null);

        // Mise à jour des dates de la grande tâche
        grandeTache.setDateDeDebutGt(dateDebutMin);
        grandeTache.setDateDeFinGt(dateFinMax);

        // Sauvegarder la mise à jour
        gtRepository.save(grandeTache);

        logger.info("Dates de la grande tâche mises à jour : Début = {}, Fin = {}", dateDebutMin, dateFinMax);
    }

    @Override
    public Map<String,Long> countGtByStatus(Date startDate, Date endDate){
        List<GrandeTache> grandeTaches;

        if(startDate != null && endDate != null) {

            grandeTaches = gtRepository.findByDateDeDebutGtBetween(startDate, endDate);
        } else {
            // Récupérer toutes les sous-tâches si aucune date n'est spécifiée
            grandeTaches = gtRepository.findAll();
        }
        return grandeTaches.stream().filter(
                gt-> gt.getEvolution() != null)
                .collect(Collectors.groupingBy(
                        gt-> gt.getEvolution().getEvolution(),
                        Collectors.counting()
                ));
    }

}
