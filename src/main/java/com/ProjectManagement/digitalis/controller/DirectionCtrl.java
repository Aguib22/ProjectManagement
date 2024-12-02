package com.ProjectManagement.digitalis.controller;

import com.ProjectManagement.digitalis.entitie.Direction;
import com.ProjectManagement.digitalis.service.DirectionService;
import com.ProjectManagement.digitalis.dto.DirectionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/direction")
public class DirectionCtrl {

    private final DirectionService directionService;

    @PostMapping("/save")
    public ResponseEntity<Direction> saveDirection(@RequestBody DirectionDto direction){
        try {
            Direction direct = directionService.save(direction);
            return new ResponseEntity<>(direct, HttpStatus.CREATED);
        }catch (RuntimeException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public DirectionCtrl(DirectionService directionService) {
        this.directionService = directionService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Direction> getDirection(@PathVariable Long id){
        Optional<Direction> direction = Optional.ofNullable(directionService.getDirection(id));

        return direction.map(direct->
            new ResponseEntity<>(direct,HttpStatus.OK)
        ).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Direction>>getAllDiection(){
        List<Direction> allDirections = directionService.getAllDirecton();

        return new ResponseEntity<>(allDirections,HttpStatus.OK);
    }


    @PutMapping("/edit/{id}")
    public  ResponseEntity<Direction> editDiection(@PathVariable Long id, @RequestBody Direction direction){

        try {
            Direction upDirection = directionService.editDirection(id,direction);
            return new ResponseEntity<>(upDirection,HttpStatus.OK);

        }catch (RuntimeException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDirection(@PathVariable Long id){
        try {
            directionService.deleteDirection(id);
            return new ResponseEntity<>("Direction supprimé avec succès!",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
