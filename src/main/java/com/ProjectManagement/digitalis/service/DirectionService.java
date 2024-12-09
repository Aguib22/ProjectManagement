package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.entitie.Direction;
import com.ProjectManagement.digitalis.entitie.Organisation;
import com.ProjectManagement.digitalis.repositorie.DirectionRepository;
import com.ProjectManagement.digitalis.repositorie.OrganisationRepository;
import com.ProjectManagement.digitalis.dto.DirectionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectionService {

    private static final Logger logger = LoggerFactory.getLogger(DirectionService.class);

    private final DirectionRepository directionRepository;
    private final OrganisationRepository organisationRepository;

    public DirectionService(DirectionRepository directionRepository, OrganisationRepository organisationRepository) {
        this.directionRepository = directionRepository;
        this.organisationRepository = organisationRepository;
    }

    public Direction save(DirectionDto directionDto) {
        logger.info("Tentative d'enregistrement de la direction avec le nom : {}", directionDto.getNomDirection());
        Organisation organisation = organisationRepository.findById(directionDto.getOrganisation())
                .orElseThrow(() -> {
                    logger.error("Échec de l'enregistrement : l'organisation avec l'id {} n'existe pas", directionDto.getOrganisation());
                    return new RuntimeException("organisation indiquée n'existe pas !");
                });

        Direction direction = new Direction();
        direction.setNomDirection(directionDto.getNomDirection());
        direction.setOrganisation(organisation);
        Direction savedDirection = directionRepository.save(direction);
        logger.info("Direction enregistrée avec succès avec l'id : {}", savedDirection.getIdDirection());
        return savedDirection;
    }

    public Direction getDirection(Long id) {
        logger.info("Tentative de récupération de la direction avec l'id : {}", id);
        return directionRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Échec de la récupération : aucune direction correspondante à l'id {}", id);
                    return new RuntimeException("aucune direction correspondante !");
                });
    }

    public List<Direction> getAllDirecton() {
        logger.info("Récupération de la liste de toutes les directions");
        List<Direction> directions = directionRepository.findAll();
        logger.info("Nombre de directions récupérées : {}", directions.size());
        return directions;
    }

    public Direction editDirection(long id, Direction direction) {
        logger.info("Tentative de modification de la direction avec l'id : {}", id);
        Direction direct = directionRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Échec de la modification : aucune direction ne correspond à l'id {}", id);
                    return new RuntimeException("Erreur! aucune direction ne correspond à cet id");
                });
        direct.setNomDirection(direction.getNomDirection());
        direct.setOrganisation(direction.getOrganisation());

        Direction updatedDirection = directionRepository.save(direct);
        logger.info("Direction avec l'id {} modifiée avec succès", updatedDirection.getIdDirection());
        return updatedDirection;
    }

    public void deleteDirection(Long id) {
        logger.info("Tentative de suppression de la direction avec l'id : {}", id);
        Direction direction = directionRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Échec de la suppression : aucune direction ne correspond à l'id {}", id);
                    return new RuntimeException("Erreur d'identifiant lors de la suppression");
                });

        directionRepository.delete(direction);
        logger.info("Direction avec l'id {} supprimée avec succès", id);
    }
}
