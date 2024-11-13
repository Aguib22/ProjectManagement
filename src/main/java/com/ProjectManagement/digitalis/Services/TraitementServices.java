package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.Entities.Traitement;

import java.util.List;

public interface TraitementServices {

    Traitement saveTraitement(Traitement traitement);

    Traitement getTraitement(Long idTraitement);

    Traitement editTraitement(Long idTraitement, Traitement traitement);

    List<Traitement> listTraitement();

    void deleteTraitement(Long idTraitement);
}
