package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.Entities.Gt;

import java.util.List;

public interface GtServices {

    Gt saveGt(Gt gt);

    Gt editGt(Long idGt, Gt gt);

    Gt getGt(Long idGt);

    List<Gt> listGt();

    void deleteGt(Long idGt);
}
