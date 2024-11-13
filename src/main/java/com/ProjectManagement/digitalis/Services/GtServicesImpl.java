package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.DAO.GtRepository;
import com.ProjectManagement.digitalis.Entities.Gt;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class GtServicesImpl implements GtServices{

    @Autowired
    private GtRepository gtRepository;



    @Override
    public Gt saveGt(Gt gt) {
        if(gt == null){
            throw new RuntimeException ("La grande tache à enregistré est null");
        }
        return gtRepository.save(gt);
    }

    @Override
    public Gt editGt(Long idGt, Gt gt) {
        Optional<Gt> optionalGt = gtRepository.findById(idGt);
        if(optionalGt.isEmpty()){
            throw new RuntimeException("La grande tache à modifier n'existe pas");
        }
        Gt gt1 = optionalGt.get();
        gt1.setNumeroGt(gt.getNumeroGt());
        gt1.setTacheGt(gt.getTacheGt());
        gt1.setChargesGt(gt.getChargesGt());
        gt1.setDateDeDebutGt(gt.getDateDeDebutGt());
        gt1.setDateDeFinGt(gt.getDateDeFinGt());
        gt1.setDateDeFinReelleGt(gt.getDateDeFinReelleGt());
        gt1.setEvolutionGt(gt.getEvolutionGt());

        return gtRepository.save(gt1);
    }

    @Override
    public Gt getGt(Long idGt) {
        Optional<Gt> optionalGt = gtRepository.findById(idGt);
        if(optionalGt.isEmpty()){
            throw new RuntimeException("la grande tache rechercher n'existe pas");
        }
        return optionalGt.get();
    }

    @Override
    public List<Gt> listGt() {
        if(listGt().isEmpty()){
            throw new RuntimeException("La liste des Grande tache est vide");
        }
        return gtRepository.findAll();
    }

    @Override
    public void deleteGt(Long idGt) {
        Optional<Gt> optionalGt = gtRepository.findById(idGt);
        if(optionalGt.isEmpty()){
            throw new RuntimeException("la grante tache à supprimer n'existe pas");
        }
        gtRepository.deleteById(idGt);

    }
}
