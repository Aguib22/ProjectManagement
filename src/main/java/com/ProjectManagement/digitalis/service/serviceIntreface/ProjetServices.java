package com.ProjectManagement.digitalis.service.serviceIntreface;

import com.ProjectManagement.digitalis.dto.ProjectDto;
import com.ProjectManagement.digitalis.entitie.Projet;
import com.ProjectManagement.digitalis.exception.ProjetError;

import java.util.List;

public interface ProjetServices {

    Projet saveProjet(Projet projet) throws ProjetError;

    Projet editProjet(Long idProjet, ProjectDto projet) throws ProjetError;

    Projet getProjet(Long idProjet) throws ProjetError;

    List<Projet> listProjet() ;

    void deleteProjet(Long idProjet) throws ProjetError;
}
