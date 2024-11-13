package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.Entities.St;

import java.util.List;

public interface StServices {

    St saveSt(St st);

    St getSt(Long idSt);

    St editSt(Long idSt, St st);

    List<St> listSt();

    void deleteSt(Long idSt);

}
