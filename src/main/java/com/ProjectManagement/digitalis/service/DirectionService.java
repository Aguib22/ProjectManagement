package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.entitie.Direction;
import com.ProjectManagement.digitalis.entitie.Organisation;
import com.ProjectManagement.digitalis.repositorie.DirectionRepository;
import com.ProjectManagement.digitalis.repositorie.OrganisationRepository;
import com.ProjectManagement.digitalis.dto.DirectionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectionService {

    @Autowired
    private final DirectionRepository directionRepository;

    private final OrganisationRepository organisationRepository;

    public Direction save(DirectionDto directionDto){
        Direction direction = new Direction();
        direction.setNomDirection(directionDto.getNomDirection());

        Organisation organisation = organisationRepository.findById(directionDto.getOrganisation())
                .orElseThrow(()->new RuntimeException("organisation indiqué n'existe pas !"));

        direction.setOrganisation(organisation);
        return directionRepository.save(direction);
    }

    public Direction getDirection(Long id){
        return directionRepository.findById(id)
                .orElseThrow(()->new RuntimeException("aucune direction correspondante !"));
    }


    public List<Direction> getAllDirecton(){
        return directionRepository.findAll();
    }

    public Direction editDirection(long id, Direction direction){
        Direction direct = directionRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Erreur! aucune direction ne correspond à cet id"));
        direct.setNomDirection(direction.getNomDirection());
        direct.setOrganisation(direction.getOrganisation());

        return directionRepository.save(direct);

    }

    public void deleteDirection(Long id){
        Direction direction = directionRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Erreur d'identifiant lors de la suppression"));

        directionRepository.delete(direction);
    }



}
