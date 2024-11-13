package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.Entities.Reunion;

import java.util.List;

public interface ReunionServices {

    Reunion saveReunion(Reunion reunion);

    Reunion getReunion(Long idReunion);

    Reunion editReunion(Long Reunion, Reunion reunion);

    List<Reunion> listReunion();

    void deleteReunion(Long idReunion);
}
