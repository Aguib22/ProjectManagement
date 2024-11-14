package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.Entities.GrandeTache;
import com.ProjectManagement.digitalis.Repositories.GtRepository;
import com.ProjectManagement.digitalis.Exception.GtError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GtServicesImpl implements GtServices{

    @Autowired
    private GtRepository gtRepository;



    @Override
    public GrandeTache saveGt(GrandeTache gt) throws GtError {
        if(gt == null){
            throw new GtError ("La grande tache à enregistré est null");
        }
        return gtRepository.save(gt);
    }

    @Override
    public GrandeTache editGt(Long idGt, GrandeTache gt) throws GtError{
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
