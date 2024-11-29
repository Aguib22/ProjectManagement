package com.ProjectManagement.digitalis.controller;

import com.ProjectManagement.digitalis.dto.GrandeTacheRequest;
import com.ProjectManagement.digitalis.entitie.GrandeTache;
import com.ProjectManagement.digitalis.entitie.Projet;
import com.ProjectManagement.digitalis.exception.GtError;
import com.ProjectManagement.digitalis.exception.ProjetError;
import com.ProjectManagement.digitalis.service.GtServices;
import com.ProjectManagement.digitalis.service.ProjetServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/gt")
@CrossOrigin("*")
public class ApiGtController {

    @Autowired
    private GtServices gtServices;
    @Autowired
    private ProjetServices projetServices;

    @PostMapping("/save")
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

    @GetMapping("/get/{idGt}")
    public GrandeTache getGt(@PathVariable Long idGt) throws GtError{
        return gtServices.getGt(idGt);
    }

    @GetMapping("/get/liste")
    public List<GrandeTache> listGt()throws GtError{
        return gtServices.listGt();
    }

    @DeleteMapping("/delete/{idGt}")
    public void deleteGt(@PathVariable Long idGt)throws GtError{
        gtServices.deleteGt(idGt);
    }


    @PutMapping("/edit/{idGt}")
    public GrandeTache editGt(@RequestBody GrandeTacheRequest grandeTache, @PathVariable Long idGt) throws GtError{

        return gtServices.editGt(idGt, grandeTache);
    }







}
