package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.dto.GrandeTacheRequest;
import com.ProjectManagement.digitalis.entitie.GrandeTache;
import com.ProjectManagement.digitalis.exception.GtError;

import java.util.List;

public interface GtServices {

    GrandeTache saveGt(GrandeTache gt) throws GtError;

    GrandeTache editGt(Long idGt, GrandeTacheRequest gt)throws GtError;

    GrandeTache getGt(Long idGt)throws GtError;

    List<GrandeTache> listGt()throws GtError;

    void deleteGt(Long idGt)throws GtError;
}
