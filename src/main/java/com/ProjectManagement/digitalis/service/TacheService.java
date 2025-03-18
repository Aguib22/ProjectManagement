package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.entitie.Tache;
import com.ProjectManagement.digitalis.repositorie.TacheRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Aguibou sow
 * @date 2025-03-16 17:01
 * @package com.ProjectManagement.digitalis.service
 * @project digitalis
 */
@Service
@Data
@RequiredArgsConstructor
public class TacheService {

    private final TacheRepository tacheRepository;


    public List<Tache> createTache(List<Tache> taches){
        return tacheRepository.saveAll(taches);
    }

    public Tache updateTache(Tache tache){
        Tache tache1 = tacheRepository.findById(tache.getId()).
                orElseThrow(()-> new RuntimeException("tache not found"));
        tache1.setFait(tache.getFait());
        return tacheRepository.save(tache1);
    }

    public List<Tache> getTachesByNoteId(Long noteId) {
        return tacheRepository.findByNoteId(noteId);
    }

    public void deleteTache(Long tacheId){
        tacheRepository.deleteById(tacheId);
    }
}
