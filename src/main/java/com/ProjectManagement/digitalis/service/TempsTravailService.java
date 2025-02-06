package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.entitie.SousTache;
import com.ProjectManagement.digitalis.entitie.TempsTravail;
import com.ProjectManagement.digitalis.repositorie.StRepository;
import com.ProjectManagement.digitalis.repositorie.TempsTravailRepository;
import org.springframework.stereotype.Service;

/**
 * @author Aguibou sow
 * @date 2025-02-02 01:02
 * @package com.ProjectManagement.digitalis.service
 * @project digitalis
 */

@Service


public class TempsTravailService {

    private final TempsTravailRepository tempsTravailRepository;

    private final StRepository stRepository;

    public TempsTravailService(TempsTravailRepository tempsTravailRepository, StRepository stRepository){
        this.tempsTravailRepository = tempsTravailRepository;
        this.stRepository = stRepository;
    }

    public TempsTravail addTempsTravail(Long sousTacheId, TempsTravail tempsTravail){

        SousTache sousTache = stRepository.findById(sousTacheId)
                .orElseThrow(() -> new RuntimeException("Sous-tâche introuvable"));

        if(sousTache.isTacheComplete()){
            return null;
        }
        tempsTravail.setSousTache(sousTache);
        TempsTravail savedTempsTravail = tempsTravailRepository.save(tempsTravail);

        return savedTempsTravail;
    }


}
