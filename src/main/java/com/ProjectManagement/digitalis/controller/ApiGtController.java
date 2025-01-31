package com.ProjectManagement.digitalis.controller;

import com.ProjectManagement.digitalis.dto.GrandeTacheRequest;
import com.ProjectManagement.digitalis.entitie.Evolution;
import com.ProjectManagement.digitalis.entitie.GrandeTache;
import com.ProjectManagement.digitalis.entitie.Projet;
import com.ProjectManagement.digitalis.exception.GtError;
import com.ProjectManagement.digitalis.exception.ProjetError;
import com.ProjectManagement.digitalis.service.serviceIntreface.EvolutionServices;
import com.ProjectManagement.digitalis.service.serviceIntreface.GtServices;
import com.ProjectManagement.digitalis.service.serviceIntreface.ProjetServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/gt")
public class ApiGtController {


    private final GtServices gtServices;
    private final ProjetServices projetServices;
    private final EvolutionServices evolutionServices;

    public ApiGtController(GtServices gtServices, ProjetServices projetServices,EvolutionServices evolutionServices) {
        this.gtServices = gtServices;
        this.projetServices = projetServices;
        this.evolutionServices = evolutionServices;
    }

    @PostMapping("/save")
    public GrandeTache grandeTacheSave(@RequestBody GrandeTacheRequest gt) throws GtError, ProjetError {


        GrandeTache grandeTache = new GrandeTache();
        grandeTache.setNomGt(gt.getNomGt());
        grandeTache.setChargesGt(gt.getChargesGt());
        grandeTache.setDateDeDebutGt(gt.getDateDeDebutGt());
        grandeTache.setDateDeFinGt(gt.getDateDeFinGt());
        grandeTache.setDateDeFinReelleGt(gt.getDateDeFinReelleGt());



        grandeTache.setProjet(gt.getProjet());


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

    @GetMapping("get-projectId/{projectId}")
   public ResponseEntity<List<GrandeTache>> getGtByProject(@PathVariable Long projectId){
        List<GrandeTache> grandeTaches = gtServices.getGtByProjectId(projectId);
        return  ResponseEntity.ok(grandeTaches);
   }





}
