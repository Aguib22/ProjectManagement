package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.Controller.Request.SousTacheRequest;
import com.ProjectManagement.digitalis.Entities.SousTache;

import java.util.List;
import java.util.Optional;

public interface StServices {

    SousTache saveSt(SousTache st);

    Optional<SousTache> getSt(Long idSt);

    SousTache editSt(Long idSt, SousTacheRequest st);

    List<SousTache> listSt();

    void deleteSt(Long idSt);

}
