package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.entitie.Reunion;
import com.ProjectManagement.digitalis.exception.ReunionError;
import com.ProjectManagement.digitalis.repositorie.ReunionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReunionServicesImpl implements ReunionServices{


    @Autowired
    private ReunionRepository reunionRepository;

    @Override
    public Reunion saveReunion(Reunion reunion) throws ReunionError {
        if(reunion ==null){
            throw new ReunionError("La reunion à enregistrer n'existe pas");
        }
        return reunionRepository.save(reunion);
    }

    @Override
    public Reunion getReunion(Long idReunion) throws ReunionError {
        Optional<Reunion> optionalReunion = reunionRepository.findById(idReunion);
        if(optionalReunion.isEmpty()){
            throw new ReunionError("La reunion rechercher n'existe pas ");
        }
        return optionalReunion.get();
    }

    @Override
    public Reunion editReunion(Long idReunion, Reunion reunion) throws ReunionError {
        Optional<Reunion> optionalReunion = reunionRepository.findById(idReunion);
        if(optionalReunion.isEmpty()){
            throw new ReunionError("La reunion à modifier n'existe pas ");
        }

        Reunion reunion1 = optionalReunion.get();
        reunion1.setDateHeureDebutReunion(reunion.getDateHeureDebutReunion());
        reunion1.setDateHeureFinReunion(reunion.getDateHeureFinReunion());
        reunion1.setOrdreDuJour(reunion.getOrdreDuJour());
        reunion1.setRemarquesR(reunion.getRemarquesR());
        reunion1.setListUser(reunion.getListUser());

        return reunionRepository.save(reunion1);
    }

    @Override
    public List<Reunion> listReunion() {
        return reunionRepository.findAll();
    }

    @Override
    public void deleteReunion(Long idReunion) throws ReunionError {
        Optional<Reunion> optionalReunion = reunionRepository.findById(idReunion);
        if(optionalReunion.isEmpty()){
            throw new ReunionError("La reunion à supprimer n'existe pas");
        }
        reunionRepository.deleteById(idReunion);

    }
}
