package com.ProjectManagement.digitalis.Controller;


import com.ProjectManagement.digitalis.Entities.Reunion;
import com.ProjectManagement.digitalis.Exception.ReunionError;
import com.ProjectManagement.digitalis.Services.ReunionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiReunionController {

    @Autowired
    private ReunionServices reunionServices;

    @PostMapping("/reunion/save")
    public Reunion saveReunion(@RequestBody Reunion reunion)throws ReunionError {
        return reunionServices.saveReunion(reunion);
    }

    @GetMapping("/reunion/get/{idReunion}")
    public Reunion getReunion(@PathVariable Long idReunion) throws ReunionError{
        return reunionServices.getReunion(idReunion);
    }

    @GetMapping("/reunion/get/liste")
    public List<Reunion> listReunion(){
        return reunionServices.listReunion();
    }

    @DeleteMapping("/reunion/delete/{idReunion}")
    public void deleteReunion(@PathVariable Long idReunion) throws ReunionError{
        reunionServices.deleteReunion(idReunion);
    }

    @PutMapping("/reunion/edit/{idReunion}")
    public Reunion editReunion(@PathVariable Long idReunion, @RequestBody Reunion reunion) throws ReunionError{
        return reunionServices.editReunion(idReunion, reunion);
    }
}
