package com.ProjectManagement.digitalis.controller;


import com.ProjectManagement.digitalis.entitie.Reunion;
import com.ProjectManagement.digitalis.exception.ReunionError;
import com.ProjectManagement.digitalis.service.ReunionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reunion")
@CrossOrigin
public class ApiReunionController {

    @Autowired
    private ReunionServices reunionServices;

    @PostMapping("/save")
    public Reunion saveReunion(@RequestBody Reunion reunion)throws ReunionError {
        return reunionServices.saveReunion(reunion);
    }

    @GetMapping("/get/{idReunion}")
    public Reunion getReunion(@PathVariable Long idReunion) throws ReunionError{
        return reunionServices.getReunion(idReunion);
    }

    @GetMapping("/get/liste")
    public List<Reunion> listReunion(){
        return reunionServices.listReunion();
    }

    @DeleteMapping("/delete/{idReunion}")
    public void deleteReunion(@PathVariable Long idReunion) throws ReunionError{
        reunionServices.deleteReunion(idReunion);
    }

    @PutMapping("/edit/{idReunion}")
    public Reunion editReunion(@PathVariable Long idReunion, @RequestBody Reunion reunion) throws ReunionError{
        return reunionServices.editReunion(idReunion, reunion);
    }
}
