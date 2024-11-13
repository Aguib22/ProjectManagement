package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.Entities.SousTache;

import java.util.List;

public interface StServices {

    SousTache saveSt(SousTache st);

    SousTache getSt(Long idSt);

    SousTache editSt(Long idSt, SousTache st);

    List<SousTache> listSt();

    void deleteSt(Long idSt);

}
