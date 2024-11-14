package com.ProjectManagement.digitalis.Controller;

import com.ProjectManagement.digitalis.Entities.GrandeTache;
import com.ProjectManagement.digitalis.Exception.GtError;
import com.ProjectManagement.digitalis.Services.GtServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ApiGtController {

    @Autowired
    private GtServices gtServices;


    @PostMapping("/gt/save")
    public GrandeTache grandeTacheSave(@RequestBody GrandeTache gt) throws GtError {
        return gtServices.saveGt(gt);
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
    public GrandeTache editGt(@RequestBody GrandeTache grandeTache, @PathVariable Long idGt) throws GtError{
        return gtServices.editGt(idGt, grandeTache);
    }







}
