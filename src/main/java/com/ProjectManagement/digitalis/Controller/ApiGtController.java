package com.ProjectManagement.digitalis.Controller;

import com.ProjectManagement.digitalis.Controller.Request.GrandeTacheRequest;
import com.ProjectManagement.digitalis.Entities.GrandeTache;
import com.ProjectManagement.digitalis.Entities.Projet;
import com.ProjectManagement.digitalis.Exception.GtError;
import com.ProjectManagement.digitalis.Exception.ProjetError;
import com.ProjectManagement.digitalis.Services.GtServices;
import com.ProjectManagement.digitalis.Services.ProjetServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ApiGtController {

    @Autowired
    private GtServices gtServices;
    @Autowired
    private ProjetServices projetServices;

    @PostMapping("/gt/save")
    public GrandeTache grandeTacheSave(@RequestBody GrandeTacheRequest gt) throws GtError, ProjetError {


        GrandeTache grandeTache = new GrandeTache();
        grandeTache.setNumeroGt(gt.getNumeroGt());
        grandeTache.setNomGt(gt.getNomGt());
        grandeTache.setChargesGt(gt.getChargesGt());
        grandeTache.setEvolutionGt(gt.getEvolutionGt());
        grandeTache.setDateDeDebutGt(gt.getDateDeDebutGt());
        grandeTache.setDateDeFinGt(gt.getDateDeFinGt());
        grandeTache.setDateDeFinReelleGt(gt.getDateDeFinReelleGt());

        Projet projet = projetServices.getProjet(gt.getProjet());
        grandeTache.setProjet(projet);


        return gtServices.saveGt(grandeTache);
    }

    @GetMapping("/gt/get/{idGt}")
    public GrandeTache getGt(@PathVariable Long idGt) throws GtError{
        return gtServices.getGt(idGt);
    }

    @GetMapping("/gt/get/liste")
    public List<GrandeTache> listGt()throws GtError{
        return gtServices.listGt();
    }

    @DeleteMapping("/gt/delete/{idGt}")
    public void deleteGt(@PathVariable Long idGt)throws GtError{
        gtServices.deleteGt(idGt);
    }


    @PutMapping("/gt/edit/{idGt}")
    public GrandeTache editGt(@RequestBody GrandeTacheRequest grandeTache, @PathVariable Long idGt) throws GtError{

        return gtServices.editGt(idGt, grandeTache);
    }







}
