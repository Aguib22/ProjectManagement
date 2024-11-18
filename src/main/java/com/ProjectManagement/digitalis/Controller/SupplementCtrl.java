package com.ProjectManagement.digitalis.Controller;

import com.ProjectManagement.digitalis.Entities.Supplement;
import com.ProjectManagement.digitalis.Services.GtServices;
import com.ProjectManagement.digitalis.Services.SupplementServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/supplement")
@CrossOrigin("*")
public class SupplementCtrl {


    @Autowired
    private SupplementServicesImpl supplementService;

    @PostMapping("/save")
    public ResponseEntity<Supplement> createSupplement(@RequestBody Supplement supplementRequest){
        Supplement supplement = supplementService.saveSupplement(supplementRequest);

        return new ResponseEntity<>(supplement, HttpStatus.CREATED) ;
    }

    // Endpoint pour récupérer un supplément par ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Supplement> getSupplementById(@PathVariable Long id) {
        try {
            Supplement supplement = supplementService.getSupplement(id);
            return new ResponseEntity<>(supplement, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint pour mettre à jour un supplément par ID
    @PutMapping("/edit/{id}")
    public ResponseEntity<Supplement> updateSupplement(@PathVariable Long id, @RequestBody Supplement supplement) {
        try {
            Supplement updatedSupplement = supplementService.edit(id, supplement);
            return new ResponseEntity<>(updatedSupplement, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint pour lister tous les suppléments
    @GetMapping("/get/liste")
    public ResponseEntity<List<Supplement>> getAllSupplements() {
        List<Supplement> supplements = supplementService.listSupplement();
        return new ResponseEntity<>(supplements, HttpStatus.OK);
    }

    // Endpoint pour supprimer un supplément par ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSupplement(@PathVariable Long id) {
        try {
            supplementService.deleteSupplement(id);
            return new ResponseEntity<>("Supplément supprimé avec succès.", HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
