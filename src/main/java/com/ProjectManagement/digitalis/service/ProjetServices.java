package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.entitie.Projet;
import com.ProjectManagement.digitalis.exception.ProjetError;

import java.util.List;

public interface ProjetServices {

    Projet saveProjet(Projet projet) throws ProjetError;

    Projet editProjet(Long idProjet, Projet projet) throws ProjetError;

    Projet getProjet(Long idProjet) throws ProjetError;

    List<Projet> listProjet() ;

    void deleteProjet(Long idProjet) throws ProjetError;
}
