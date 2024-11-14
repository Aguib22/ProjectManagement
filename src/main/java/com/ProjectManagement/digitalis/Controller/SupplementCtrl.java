package com.ProjectManagement.digitalis.Controller;

import com.ProjectManagement.digitalis.Entities.Supplement;
import com.ProjectManagement.digitalis.Services.GtServices;
import com.ProjectManagement.digitalis.Services.SupplementServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class SupplementCtrl {


    @Autowired
    private SupplementServicesImpl supplementServices;

    @PostMapping("/createSupplement")
    public ResponseEntity<Supplement> createSupplement(@RequestBody Supplement supplement){
        Supplement supplement1 = supplementServices.saveSupplement(supplement);

        return ResponseEntity.ok(supplement1);
    }



}
