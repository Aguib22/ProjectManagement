package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.Entities.Projet;

import java.util.List;

public interface ProjetServices {

    Projet saveProjet(Projet projet);

    Projet editProjet(Long idProjet, Projet projet);

    Projet getProjet(Long idProjet);

    List<Projet> listProjet();

    void deleteProjet(Long idProjet);
}
