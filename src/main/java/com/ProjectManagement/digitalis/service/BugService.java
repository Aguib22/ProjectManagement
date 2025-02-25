package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.dto.BugDto;
import com.ProjectManagement.digitalis.entitie.*;
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

        Bug bugSaved = bugRepository.save(savedBug);
        this.updateSousTacheStatusIfBugPending(sousTache.getIdSt());
        return bugSaved;

    }
    public List<Bug> getAllBugs() {
        return bugRepository.findAll();
    }

    public List<Bug> getBugsBySt(Long idSt){
        SousTache sousTache = stRepository.findById(idSt).orElseThrow();
        this.updateSousTacheStatusIfBugPending(idSt);

        return bugRepository.findBySousTache(sousTache);

    }

    public Bug ubdateBugStatus(Long bugId,BugStatus status){
        Bug bug = bugRepository.findById(bugId).orElseThrow();
        bug.setStatus(status);
        SousTache sousTache = bug.getSousTache();
        Bug updatedBug = bugRepository.save(bug);
        this.updateSousTacheStatusIfBugPending(sousTache.getIdSt());
        return updatedBug;

    }

    /*public void updateSousTacheStatusIfBugPending(Long sousTacheId) {
        SousTache sousTache = stRepository.findById(sousTacheId)
                .orElseThrow(() -> new RuntimeException("Sous-tâche non trouvée"));

        boolean hasPendingBug = sousTache.getBugs().stream()
                .anyMatch(bug -> bug.getStatus() == BugStatus.EN_ATTENTE);

        if (hasPendingBug) {
            if (!"initiale".equals(sousTache.getEvolution().getEvolution())) {
                Evolution evolution = new Evolution();
                evolution.setIdTraitement(3L);
                evolution.setEvolution("initiale");
                sousTache.setEvolution(evolution); // Mettez à jour l'état initial
                stRepository.save(sousTache);
            }
        } else {
            if (!"En teste".equals(sousTache.getEvolution().getEvolution())) {
                Evolution evolution = new Evolution();
                evolution.setIdTraitement(4L);
                evolution.setEvolution("En teste");
                sousTache.setEvolution(evolution);
                stRepository.save(sousTache);
            }
        }
    }*/

    public void updateSousTacheStatusIfBugPending(Long sousTacheId) {
        SousTache sousTache = stRepository.findById(sousTacheId)
                .orElseThrow(() -> new RuntimeException("Sous-tâche non trouvée"));

        // Vérifier s'il y a au moins un bug en attente


        if (!this.canMoveSousTache(sousTache.getIdSt())) {
            Evolution evolution = new Evolution();
            evolution.setEvolution("initiale");
            evolution.setIdTraitement(3L);
            sousTache.setEvolution(evolution); // Bloquer la tâche si un bug est en attente
        }

        stRepository.save(sousTache); // Sauvegarder la sous-tâche avec son nouveau statut
    }

    public boolean canMoveSousTache(Long sousTacheId) {
        SousTache sousTache = stRepository.findById(sousTacheId)
                .orElseThrow(() -> new RuntimeException("Sous-tâche non trouvée"));

        // Récupérer tous les bugs liés à cette sous-tâche
        List<Bug> bugs = bugRepository.findBySousTache(sousTache);

        // 🔥 Si aucun bug n'est lié à la sous-tâche, elle peut être déplacée
        if (bugs.isEmpty()) {
            return true;
        }

        // 🔥 Vérifier s'il y a des bugs en attente
        boolean hasPendingBug = bugs.stream().anyMatch(bug -> bug.getStatus() == BugStatus.EN_ATTENTE);

        // Si un bug est encore en attente, la sous-tâche ne peut pas être déplacée
        return !hasPendingBug;
    }
}
