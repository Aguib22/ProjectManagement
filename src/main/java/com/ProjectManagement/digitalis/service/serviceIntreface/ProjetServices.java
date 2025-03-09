package com.ProjectManagement.digitalis.service.serviceIntreface;

import com.ProjectManagement.digitalis.dto.ProjectDto;
import com.ProjectManagement.digitalis.entitie.GrandeTache;
import com.ProjectManagement.digitalis.entitie.Projet;
import com.ProjectManagement.digitalis.entitie.User;
import com.ProjectManagement.digitalis.exception.GtError;
import com.ProjectManagement.digitalis.exception.ProjetError;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface ProjetServices {

    Projet saveProjet(Projet projet, MultipartFile file,String fileName) throws ProjetError, IOException;

    void editProjet(Long idProjet, ProjectDto projet) throws ProjetError;

    Projet getProjet(Long idProjet) throws ProjetError;

    List<Projet> listProjet(Date startDate, Date endDate) ;

    void deleteProjet(Long idProjet) throws ProjetError;

    void updateProjetDates(Projet projet) throws GtError;

    List<GrandeTache> getGtByProjectId(Long projectId);

    List<User> getUsersByProjetId(Long projetId);
}
