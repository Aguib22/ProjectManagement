package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.Entities.SousTache;
import com.ProjectManagement.digitalis.Repositories.StRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StServicesImpl implements StServices {

    private final StRepository stRepository;
    @Override
    public SousTache saveSt(SousTache st) {
        return stRepository.save(st);
    }

    @Override
    public Optional<SousTache> getSt(Long idSt) {
        return stRepository.findById(idSt);
    }

    @Override
    public SousTache editSt(Long idSt, SousTache st) {
        return stRepository.findById(idSt).map(
                sousTache -> {
                    sousTache.setGt(sousTache.getGt());
                    sousTache.setNumeroSt(st.getNumeroSt());
                    sousTache.setTacheSt(st.getTacheSt());
                    sousTache.setDateDeDebutSt(st.getDateDeDebutSt());
                    sousTache.setDateDeFinSt(st.getDateDeFinSt());
                    sousTache.setDateDeFinReelleSt(st.getDateDeFinReelleSt());
                    sousTache.setSurchargesGt(st.getSurchargesGt());
                    sousTache.setRemarquesGt(st.getRemarquesGt());


                    return stRepository.save(sousTache);
                }).orElseThrow(()->new RuntimeException("Erreur de modification!") );
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
