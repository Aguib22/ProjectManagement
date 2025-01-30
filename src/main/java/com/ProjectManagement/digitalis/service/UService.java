package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.entitie.Direction;
import com.ProjectManagement.digitalis.entitie.UserService;
import com.ProjectManagement.digitalis.repositorie.DirectionRepository;
import com.ProjectManagement.digitalis.repositorie.UserServiceRepository;
import com.ProjectManagement.digitalis.dto.ServiceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j  // Activation des logs avec SLF4J
@Service
public class UService {

    private final UserServiceRepository userServiceRepository;
    private final DirectionRepository directionRepository;

    public UService(UserServiceRepository userServiceRepository, DirectionRepository directionRepository) {
        this.userServiceRepository = userServiceRepository;
        this.directionRepository = directionRepository;
    }

    public UserService save(ServiceDto userServiceDto) {
        log.info("Enregistrement du service : {}", userServiceDto);
        UserService userService = new UserService();
        userService.setNomUserService(userServiceDto.getNomUserService());

        Direction direction = directionRepository.findById(userServiceDto.getDirection())
                .orElseThrow(() -> {
                    log.error("Direction avec l'ID : {} n'existe pas", userServiceDto.getDirection());
                    return new RuntimeException("Direction indiquée n'existe pas !");
                });

        userService.setDirection(direction);
        UserService savedUserService = userServiceRepository.save(userService);
        log.info("Service enregistré avec succès : {}", savedUserService);

        return savedUserService;
    }

    public UserService getDirection(Long id) {
        log.info("Récupération du service avec l'ID : {}", id);
        return userServiceRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Aucun service trouvé avec l'ID : {}", id);
                    return new RuntimeException("Aucun service correspondant !");
                });
    }

    public List<UserService> getAllService() {
        log.info("Récupération de tous les services");
        List<UserService> services = userServiceRepository.findAll();
        log.info("Nombre de services récupérés : {}", services.size());
        return services;
    }

    public UserService editService(long id, UserService service) {
        log.info("Modification du service avec l'ID : {}", id);
        UserService serv = userServiceRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Aucun service trouvé avec l'ID : {}", id);
                    return new RuntimeException("Erreur! Aucun service ne correspond à cet ID");
                });

        serv.setNomUserService(service.getNomUserService());
        serv.setDirection(service.getDirection());
        UserService updatedService = userServiceRepository.save(serv);
        log.info("Service modifié avec succès : {}", updatedService);

        return updatedService;
    }

    public void deleteService(Long id) {
        log.info("Tentative de suppression du service avec l'ID : {}", id);
        UserService service = userServiceRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Erreur d'identifiant lors de la suppression du service avec l'ID : {}", id);
                    return new RuntimeException("Erreur d'identifiant lors de la suppression");
                });

        userServiceRepository.delete(service);
        log.info("Service supprimé avec succès avec l'ID : {}", id);
    }

    public List<UserService> getServicesByDirection(Long directionId) {
        Direction direction = directionRepository.findById(directionId)
                .orElseThrow(() -> new RuntimeException("Direction introuvable pour l'ID : " + directionId));
        return userServiceRepository.findByDirection(direction);
    }

}
