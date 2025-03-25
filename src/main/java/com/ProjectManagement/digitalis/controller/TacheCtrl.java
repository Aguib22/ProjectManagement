package com.ProjectManagement.digitalis.controller;

import com.ProjectManagement.digitalis.entitie.Tache;
import com.ProjectManagement.digitalis.repositorie.TacheRepository;
import com.ProjectManagement.digitalis.service.TacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Aguibou sow
 * @date 2025-03-16 17:24
 * @package com.ProjectManagement.digitalis.controller
 * @project digitalis
 */
@RestController
@RequestMapping("/api/tache")
@RequiredArgsConstructor
public class TacheCtrl {

    private final TacheService tacheService;
    private final TacheRepository tacheRepository;


    @GetMapping("/note/{noteId}")
    public List<Tache> getTachesByNoteId(@PathVariable Long noteId) {
        return tacheService.getTachesByNoteId(noteId);
    }

    @PutMapping("/update/{tacheId}")
    public ResponseEntity<Tache> updateTache(@PathVariable Long tacheId, @RequestBody Tache tache){
        Tache tache1 = tacheRepository.findById(tacheId).
                orElseThrow();
        tache1.setFait(tache.getFait());
        tacheService.updateTache(tache1);
        return ResponseEntity.ok(tache1);
    }

    public String deleteTache(@PathVariable Long tacheId){
        tacheService.deleteTache(tacheId);
        return "tache supprimée avec succès!";
    }
}
