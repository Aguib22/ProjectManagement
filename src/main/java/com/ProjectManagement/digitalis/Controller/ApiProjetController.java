package com.ProjectManagement.digitalis.Controller;

import com.ProjectManagement.digitalis.Entities.Projet;
import com.ProjectManagement.digitalis.Exception.ProjetError;
import com.ProjectManagement.digitalis.Services.ProjetServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projet")
@CrossOrigin
public class ApiProjetController {

    @Autowired
    private ProjetServices projetServices;


    @PostMapping("/save")
    public Projet projetSave(@RequestBody Projet projet)throws ProjetError {
        return projetServices.saveProjet(projet);
    }

    @GetMapping("get/{idProjet}")
    public Projet getProjet(@PathVariable Long idProjet) throws ProjetError{
        return projetServices.getProjet(idProjet);
    }

    @GetMapping("get/liste")
    public List<Projet> listProjet(){
        return projetServices.listProjet();
    }

    @DeleteMapping("delete/{idProjet}")
    public void deleteProjet(@PathVariable Long idProjet) throws ProjetError{
        projetServices.deleteProjet(idProjet);
    }

    @PutMapping("edit/{idProjet}")
    public Projet editProjet(@PathVariable Long idProjet, @RequestBody Projet projet) throws ProjetError{
        return projetServices.editProjet(idProjet, projet);
    }


}
