package com.ProjectManagement.digitalis.controller;

import com.ProjectManagement.digitalis.entitie.Organisation;
import com.ProjectManagement.digitalis.service.OrganisationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/organisation")
public class OrganisationCtrl {

    private final OrganisationService organisationService;

    @PostMapping("/save")
    public ResponseEntity<Organisation> saveOrganisation(@RequestBody Organisation organisation){

        try {
            Organisation org = organisationService.saveOrganisation(organisation);
            return new ResponseEntity<>(org, HttpStatus.CREATED);
        }catch (RuntimeException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Organisation> getOrganisation(@PathVariable Long id){
        Optional<Organisation> organisation = Optional.ofNullable(organisationService.getOrganisation(id));
        return organisation.map(org-> new ResponseEntity<>(org,HttpStatus.OK))
                .orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }


    @GetMapping("get/all")
    public ResponseEntity<List<Organisation>> getAllOrganisation(){
        List<Organisation> allOrgs = organisationService.getAllOrganisation();

        return new ResponseEntity<>(allOrgs,HttpStatus.OK);
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<Organisation> editOrgansation(@PathVariable Long id,@RequestBody Organisation organisation){
        try {
            Organisation orgUpd = organisationService.editOrganisation(id,organisation);
            return new ResponseEntity<>(orgUpd,HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrganisation(@PathVariable Long id){
        try {
            organisationService.deleteOrganisation(id);
            return new ResponseEntity<>("Organisation supprimé avec succès!",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
