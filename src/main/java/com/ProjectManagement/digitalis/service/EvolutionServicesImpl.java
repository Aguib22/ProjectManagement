package com.ProjectManagement.digitalis.service;


import com.ProjectManagement.digitalis.entitie.Evolution;
import com.ProjectManagement.digitalis.repositorie.EvolutionRepository;
import com.ProjectManagement.digitalis.service.serviceIntreface.EvolutionServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvolutionServicesImpl implements EvolutionServices {
    public EvolutionServicesImpl(EvolutionRepository evolutionServices) {
        this.evolutionServices = evolutionServices;
    }

    private final EvolutionRepository evolutionServices;



    @Override
    public Evolution saveTraitement(Evolution traitement) {
        return evolutionServices.save(traitement);
    }

    @Override
    public Optional<Evolution> getTraitement(Long idTraitement) {
        return evolutionServices.findById(idTraitement);
    }

    @Override
    public Evolution editTraitement(Long idTraitement, Evolution traitement) {
        return evolutionServices.findById(idTraitement)
                .map(evolution -> {
                    evolution.setEvolution(traitement.getEvolution());

                    return evolutionServices.save(evolution);
                })
                .orElseThrow(() -> new RuntimeException("Evolution not found"));

    }

    @Override
    public List<Evolution> listTraitement() {

        return evolutionServices.findAll();
    }

    @Override
    public void deleteTraitement(Long idTraitement) {

        evolutionServices.deleteById(idTraitement);
        System.out.println("suppression du status effectué avec succès !");

    }
}
