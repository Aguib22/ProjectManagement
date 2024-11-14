package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.Entities.Reunion;
import com.ProjectManagement.digitalis.Exception.ReunionError;

import java.util.List;

public interface ReunionServices {

    Reunion saveReunion(Reunion reunion) throws ReunionError;

    Reunion getReunion(Long idReunion) throws ReunionError;

    Reunion editReunion(Long idReunion, Reunion reunion) throws ReunionError;

    List<Reunion> listReunion() ;

    void deleteReunion(Long idReunion) throws ReunionError;
}
