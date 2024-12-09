package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.entitie.Organisation;
import com.ProjectManagement.digitalis.repositorie.OrganisationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrganisationService {

    private final OrganisationRepository organisationRepository;

    public OrganisationService(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    public Organisation saveOrganisation(Organisation organisation){
        log.info("Enregistrement de l'organisation : {}", organisation.getNomOrganisation());
        return organisationRepository.save(organisation);
    }

    public Organisation getOrganisation(Long id){
        log.info("Récupération de l'organisation avec l'ID : {}", id);
        return organisationRepository.findById(id).orElseThrow(
                ()-> {
                    log.error("Aucune organisation trouvée pour l'ID : {}", id);
                    return new RuntimeException("Aucune organisation correspondante");
                }
        );
    }

    public List<Organisation> getAllOrganisation(){
        log.info("Récupération de toutes les organisations");
        return organisationRepository.findAll();
    }

    public Organisation editOrganisation(Long id, Organisation organisation) {
        log.info("Modification de l'organisation avec l'ID : {}", id);
        Organisation org = organisationRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Organisation avec l'ID {} non trouvée", id);
                    return new RuntimeException("Erreur: aucune organisation");
                });

        org.setNomOrganisation(organisation.getNomOrganisation());
        log.info("Organisation mise à jour : {}", org.getNomOrganisation());

        return organisationRepository.save(org);
    }

    public void deleteOrganisation(Long id){
        log.info("Suppression de l'organisation avec l'ID : {}", id);
        Organisation organisation = organisationRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Erreur de suppression : organisation avec l'ID {} non trouvée", id);
                    return new RuntimeException("Erreur de suppression de l'organisation: au id correspondant !");
                }
        );

        organisationRepository.deleteById(id);
        log.info("Organisation supprimée avec succès : {}", organisation.getNomOrganisation());
    }
}
