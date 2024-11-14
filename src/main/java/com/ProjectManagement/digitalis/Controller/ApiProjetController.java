package com.ProjectManagement.digitalis.Controller;

import com.ProjectManagement.digitalis.Entities.Projet;
import com.ProjectManagement.digitalis.Exception.ProjetError;
import com.ProjectManagement.digitalis.Services.ProjetServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiProjetController {

    @Autowired
    private ProjetServices projetServices;


    @PostMapping("/projet/save")
    public Projet projetSave(@RequestBody Projet projet)throws ProjetError {
        return projetServices.saveProjet(projet);
    }

    @GetMapping("/projet/get/{idProjet}")
    public Projet getProjet(@PathVariable Long idProjet) throws ProjetError{
        return projetServices.getProjet(idProjet);
    }

    @GetMapping("/projet/get/liste")
    public List<Projet> listProjet(){
        return projetServices.listProjet();
    }

    @DeleteMapping("/projet/delete/{idProjet}")
    public void deleteProjet(@PathVariable Long idProjet) throws ProjetError{
        projetServices.deleteProjet(idProjet);
    }

    @PutMapping("/projet/delete/{idProjet")
    public Projet editProjet(@PathVariable Long idProjet, @RequestBody Projet projet) throws ProjetError{
        return projetServices.editProjet(idProjet, projet);
    }


}
