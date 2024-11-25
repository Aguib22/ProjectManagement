package com.ProjectManagement.digitalis.Controller;


import com.ProjectManagement.digitalis.Entities.UserService;
import com.ProjectManagement.digitalis.Services.UService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service")
public class UserviceCtrl {


    @Autowired
    private final UService uService;

    @PostMapping("/save")
    public ResponseEntity<UserService> saveDirection(@RequestBody UserService service){
        try {
            UserService serv = uService.save(service);
            return new ResponseEntity<>(serv, HttpStatus.CREATED);
        }catch (RuntimeException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<UserService> getService(@PathVariable Long id){
        Optional<UserService> direction = Optional.ofNullable(uService.getDirection(id));

        return direction.map(direct->
                new ResponseEntity<>(direct,HttpStatus.OK)
        ).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<UserService>>getAllService(){
        List<UserService> allDirections = uService.getAllService();

        return new ResponseEntity<>(allDirections,HttpStatus.OK);
    }


    @PutMapping("/edit/{id}")
    public  ResponseEntity<UserService> editService(@PathVariable Long id, @RequestBody UserService service){

        try {
            UserService upservice = uService.editService(id,service);
            return new ResponseEntity<>(upservice,HttpStatus.OK);

        }catch (RuntimeException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteService(@PathVariable Long id){
        try {
            uService.deleteService(id);
            return new ResponseEntity<>("service supprimé avec succès!",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
