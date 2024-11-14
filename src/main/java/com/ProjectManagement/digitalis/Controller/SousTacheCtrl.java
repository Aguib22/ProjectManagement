package com.ProjectManagement.digitalis.Controller;


import com.ProjectManagement.digitalis.Entities.SousTache;
import com.ProjectManagement.digitalis.Services.StServicesImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/sous-tache")
public class SousTacheCtrl {
    @Autowired
    private final StServicesImpl stServicesImpl;

    @PostMapping("/créer")
    public ResponseEntity<SousTache> createSousTache(@RequestBody SousTache sousTache) {
        SousTache createdSousTache = stServicesImpl.saveSt(sousTache);
        return new ResponseEntity<>(createdSousTache, HttpStatus.CREATED);
    }

    // Endpoint pour récupérer une sous-tâche par son ID
    @GetMapping("/get/{idSt}")
    public ResponseEntity<SousTache> getSousTache(@PathVariable Long idSt) {
        Optional<SousTache> sousTacheOptional = stServicesImpl.getSt(idSt);
        return sousTacheOptional.map(sousTache -> new ResponseEntity<>(sousTache, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint pour modifier une sous-tâche existante
    @PutMapping("/edit/{idSt}")
    public ResponseEntity<SousTache> updateSousTache(@PathVariable Long idSt, @RequestBody SousTache sousTache) {
        try {
            SousTache updatedSousTache = stServicesImpl.editSt(idSt, sousTache);
            return new ResponseEntity<>(updatedSousTache, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint pour récupérer la liste de toutes les sous-tâches
    @GetMapping
    public ResponseEntity<List<SousTache>> getAllSousTaches() {
        List<SousTache> sousTaches = stServicesImpl.listSt();
        return new ResponseEntity<>(sousTaches, HttpStatus.OK);
    }

    // Endpoint pour supprimer une sous-tâche par son ID
    @DeleteMapping("/del/{idSt}")
    public ResponseEntity<String> deleteSousTache(@PathVariable Long idSt) {
        try {
            stServicesImpl.deleteSt(idSt);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
