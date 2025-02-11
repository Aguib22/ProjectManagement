package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.dto.BugDto;
import com.ProjectManagement.digitalis.entitie.Bug;
import com.ProjectManagement.digitalis.entitie.BugStatus;
import com.ProjectManagement.digitalis.entitie.SousTache;
import com.ProjectManagement.digitalis.entitie.User;
import com.ProjectManagement.digitalis.repositorie.BugRepository;
import com.ProjectManagement.digitalis.repositorie.StRepository;
import com.ProjectManagement.digitalis.repositorie.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.ClientInfoStatus;
import java.util.List;

/**
 * @author Aguibou sow
 * @date 2025-02-09 00:00
 * @package com.ProjectManagement.digitalis.service
 * @project digitalis
 */
@Service
public class BugService {

    private final BugRepository bugRepository;

    private final StRepository stRepository;

    private final UserRepository userRepository;

    public BugService(BugRepository bugRepository, StRepository stRepository, UserRepository userRepository) {
        this.bugRepository = bugRepository;
        this.stRepository = stRepository;
        this.userRepository = userRepository;
    }

    public Bug bugReport(BugDto bug){
        User testeur = userRepository.findById(bug.getTesteur())
                .orElseThrow(()-> new RuntimeException("erreur!"));

        SousTache sousTache = stRepository.findById(bug.getSousTache())
                .orElseThrow(()->new RuntimeException("Sous tache non trouvée"));

        Bug savedBug = new Bug();
        savedBug.setDescription(bug.getDescription());
        savedBug.setCaptureUrl(bug.getCaptureUrl());
        savedBug.setTesteur(testeur);
        savedBug.setSousTache(sousTache);
        savedBug.setStatus(BugStatus.EN_ATTENTE);

        return bugRepository.save(savedBug);

    }
    public List<Bug> getAllBugs() {
        return bugRepository.findAll();
    }

    public List<Bug> getBugsBySt(Long idSt){
        SousTache sousTache = stRepository.findById(idSt).orElseThrow();

        return bugRepository.findBySousTache(sousTache);

    }

    public Bug ubdateBugStatus(Long bugId,BugStatus status){
        Bug bug = bugRepository.findById(bugId).orElseThrow();
        bug.setStatus(status);
        return bugRepository.save(bug);

    }

}
