package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.Entities.Evolution;

import java.util.List;
import java.util.Optional;

public interface EvolutionServices {

    Evolution saveTraitement(Evolution traitement);

    Optional<Evolution> getTraitement(Long idTraitement);

    Evolution editTraitement(Long idTraitement, Evolution traitement);

    List<Evolution> listTraitement();

    void deleteTraitement(Long idTraitement);
}
