package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.Entities.Organisation;
import com.ProjectManagement.digitalis.Repositories.OrganisationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganisationService {

    private final OrganisationRepository organisationRepository;

    public Organisation saveOrganisation(Organisation organisation){
        return organisationRepository.save(organisation);
    }

    public Organisation getOrganisation(Long id){
        return organisationRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Aucune organisation correspondante")
        );
    }

    public List<Organisation> getAllOrganisation(){
        return organisationRepository.findAll();
    }

    public Organisation editOrganisation(Long id, Organisation organisation) {
       Organisation org = organisationRepository.findById(id)
               .orElseThrow(()-> new RuntimeException("Erreur: aucune oranisation"));

       org.setNomOrganisation(organisation.getNomOrganisation());


        return organisationRepository.save(org);

    }

    public void deleteOrganisation(Long id){
        Organisation organisation = organisationRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Erreur de suppression de l'organisation:au id correspondant !")
        );

        organisationRepository.deleteById(id);
    }
}