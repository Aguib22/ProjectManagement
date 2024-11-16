package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.dto.SousTacheRequest;
import com.ProjectManagement.digitalis.Entities.*;
import com.ProjectManagement.digitalis.Repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StServicesImpl implements StServices {

    @Autowired
    private StRepository stRepository;

    @Autowired
    private GtRepository gtRepository;

    @Autowired
    private EvolutionRepository evolutionRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public SousTache saveSt(SousTache st) {
        return stRepository.save(st);
    }

    @Override
    public Optional<SousTache> getSt(Long idSt) {
        return stRepository.findById(idSt);
    }

    @Override
    public SousTache editSt(Long idSt, SousTacheRequest st) {
        Optional<SousTache> optionalSt = stRepository.findById(idSt);
        if(optionalSt.isEmpty()){
            //throw new RuntimeException("La grande tache à modifier n'existe pas");
        }

        SousTache st1 = optionalSt.get();
        st1.setNumeroSt(st.getNumeroSt());
        st1.setTacheSt(st.getTacheSt());
        st1.setChargesSt(st.getChargesSt());
        st1.setDateDeDebutSt(st.getDateDeDebutSt());
        st1.setDateDeFinSt(st.getDateDeFinSt());
        st1.setDateDeFinReelleSt(st.getDateDeFinReelleSt());
        st1.setEvolutionSt(st.getEvolutionSt());
        st1.setSurchargesGt(st.getSurchargesGt());
        st1.setRemarquesGt(st.getRemarquesGt());

        GrandeTache grandeTache = gtRepository.findById(st.getGt()).get();
        st1.setGt(grandeTache);

        Evolution evolution = evolutionRepository.findById(st.getEvolution()).get();
        st1.setTraitement(evolution);

        User user = userRepository.findById(st.getUser()).get();
        st1.setUser(user);

        return stRepository.save(st1);
    }

    @Override
    public List<SousTache> listSt() {
        return stRepository.findAll();
    }

    @Override
    public void deleteSt(Long idSt) {
        if(stRepository.existsById(idSt)){
            stRepository.deleteById(idSt);
        }else {

            throw new RuntimeException("aucune sous tache corespondantes");
        }

    }
}
