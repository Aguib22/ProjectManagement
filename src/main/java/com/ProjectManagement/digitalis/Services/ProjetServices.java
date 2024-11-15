package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.Entities.Projet;
import com.ProjectManagement.digitalis.Exception.ProjetError;

import java.util.List;

public interface ProjetServices {

    Projet saveProjet(Projet projet) throws ProjetError;

    Projet editProjet(Long idProjet, Projet projet) throws ProjetError;

    Projet getProjet(Long idProjet) throws ProjetError;

    List<Projet> listProjet() ;

    void deleteProjet(Long idProjet) throws ProjetError;
}
