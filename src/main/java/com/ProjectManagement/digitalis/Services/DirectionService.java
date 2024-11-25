package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.Entities.Direction;
import com.ProjectManagement.digitalis.Repositories.DirectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectionService {

    @Autowired
    private final DirectionRepository directionRepository;

    public Direction save(Direction direction){
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
