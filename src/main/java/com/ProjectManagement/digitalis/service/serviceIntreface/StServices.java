package com.ProjectManagement.digitalis.service.serviceIntreface;

import com.ProjectManagement.digitalis.dto.SousTacheRequest;
import com.ProjectManagement.digitalis.entitie.SousTache;
import com.ProjectManagement.digitalis.dto.StUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface StServices {



    SousTache saveSousTache(SousTacheRequest sousTacheRequest);

    Optional<SousTache> getSt(Long idSt);



    SousTache editSt(Long idSt, StUpdateRequest st);

    List<SousTache> listSt();

    void deleteSt(Long idSt);

}
