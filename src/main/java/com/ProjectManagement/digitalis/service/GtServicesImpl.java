package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.dto.GrandeTacheRequest;
import com.ProjectManagement.digitalis.entitie.GrandeTache;
import com.ProjectManagement.digitalis.entitie.Projet;
import com.ProjectManagement.digitalis.repositorie.GtRepository;
import com.ProjectManagement.digitalis.exception.GtError;
import com.ProjectManagement.digitalis.repositorie.ProjetRepository;
import com.ProjectManagement.digitalis.service.serviceIntreface.GtServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GtServicesImpl implements GtServices {


    private GtRepository gtRepository;
    private ProjetRepository projetRepository;

    public GtServicesImpl(GtRepository gtRepository, ProjetRepository projetRepository) {
        this.gtRepository = gtRepository;
        this.projetRepository = projetRepository;
    }


    @Override
    public GrandeTache saveGt(GrandeTache gt) throws GtError {
        if(gt == null){
            throw new GtError ("La grande tache à enregistré est null");
        }
        return gtRepository.save(gt);
    }

    @Override
    public GrandeTache editGt(Long idGt, GrandeTacheRequest gt) throws GtError{
        Optional<GrandeTache> optionalGt = gtRepository.findById(idGt);
        if(optionalGt.isEmpty()){
            throw new GtError("La grande tache à modifier n'existe pas");
        }
        GrandeTache gt1 = optionalGt.get();
        gt1.setNumeroGt(gt.getNumeroGt());
        gt1.setNomGt(gt.getNomGt());
        gt1.setChargesGt(gt.getChargesGt());
        gt1.setDateDeDebutGt(gt.getDateDeDebutGt());
        gt1.setDateDeFinGt(gt.getDateDeFinGt());
        gt1.setDateDeFinReelleGt(gt.getDateDeFinReelleGt());
        gt1.setEvolutionGt(gt.getEvolutionGt());

        Projet projet = projetRepository.findById(gt.getProjet()).get();
        gt1.setProjet(projet);

        return gtRepository.save(gt1);
    }

    @Override
    public GrandeTache getGt(Long idGt) throws GtError{
        Optional<GrandeTache> optionalGt = gtRepository.findById(idGt);
        if(optionalGt.isEmpty()){
            throw new GtError("la grande tache rechercher n'existe pas");
        }
        return optionalGt.get();
    }

    @Override
    public List<GrandeTache> listGt() throws GtError{

        return gtRepository.findAll();
    }

    @Override
    public void deleteGt(Long idGt) throws GtError{
        Optional<GrandeTache> optionalGt = gtRepository.findById(idGt);
        if(optionalGt.isEmpty()){
            throw new GtError("la grante tache à supprimer n'existe pas");
        }
        gtRepository.deleteById(idGt);

    }
}
