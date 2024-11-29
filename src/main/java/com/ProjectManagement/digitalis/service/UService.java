package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.entitie.Direction;
import com.ProjectManagement.digitalis.entitie.UserService;
import com.ProjectManagement.digitalis.repositorie.DirectionRepository;
import com.ProjectManagement.digitalis.repositorie.UserServiceRepository;
import com.ProjectManagement.digitalis.dto.ServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UService {


    @Autowired
    private final UserServiceRepository userServiceRepository;

    private final DirectionRepository directionRepository;
    public UserService save(ServiceDto userServiceDto) {
        UserService userService = new UserService();
        userService.setNomUserService(userServiceDto.getNomUserService());

        Direction direction = directionRepository.findById(userServiceDto.getDirection())
                .orElseThrow(() -> new RuntimeException("Direction indiquée n'existe pas !"));

        userService.setDirection(direction);

        return userServiceRepository.save(userService);
    }


    public UserService getDirection(Long id){
        return userServiceRepository.findById(id)
                .orElseThrow(()->new RuntimeException("aucun service correspondante !"));
    }


    public List<UserService> getAllService(){
        return userServiceRepository.findAll();
    }

    public UserService editService(long id, UserService service){
        UserService serv = userServiceRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Erreur! aucun service ne correspond à cet id"));
        serv.setNomUserService(service.getNomUserService());
        serv.setDirection(service.getDirection());

        return userServiceRepository.save(serv);

    }

    public void deleteService(Long id){
        UserService service = userServiceRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Erreur d'identifiant lors de la suppression"));

        userServiceRepository.delete(service);
    }
}
