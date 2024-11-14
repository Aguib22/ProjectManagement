package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.Entities.Projet;
import com.ProjectManagement.digitalis.Exception.GtError;
import com.ProjectManagement.digitalis.Exception.ProjetError;
import com.ProjectManagement.digitalis.Repositories.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ProjetServicesImpl implements ProjetServices{

    @Autowired
    private ProjetRepository projetRepository;

    @Override
    public Projet saveProjet(Projet projet) throws ProjetError {
        if(projet == null){
        throw new ProjetError("Le Projet à enregistré est null");
    }
        return projetRepository.save(projet);
    }

    @Override
    public Projet editProjet(Long idProjet, Projet projet) throws ProjetError{
        Optional<Projet> optionalProjet = projetRepository.findById(idProjet);
        if(optionalProjet.isEmpty()){
            throw new ProjetError("Le projet à modifier n'existe pas");
        }

        Projet projet1 = optionalProjet.get();
        projet1.setDescProjet(projet.getDescProjet());
        projet1.setNomProjet(projet.getNomProjet());
        projet1.setDateDebutProjet(projet.getDateDebutProjet());
        projet1.setDateFinProject(projet.getDateFinProject());
        projet1.setListGt(projet.getListGt());


        return projetRepository.save(projet1);
    }

    @Override
    public Projet getProjet(Long idProjet) throws ProjetError {
        Optional<Projet> optionalProjet = projetRepository.findById(idProjet);
        if(optionalProjet.isEmpty()){
            throw new ProjetError("Le projet rechercher n'existe pas");
        }
        return optionalProjet.get();
    }

    @Override
    public List<Projet> listProjet() {

        return projetRepository.findAll();
    }

    @Override
    public void deleteProjet(Long idProjet) throws ProjetError{
        Optional<Projet> optionalProjet = projetRepository.findById(idProjet);
        if(optionalProjet.isEmpty()){
            throw new ProjetError("Le projet à supprimmer n'existe pas");
        }
        projetRepository.deleteById(idProjet);

    }
}
