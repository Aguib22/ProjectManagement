package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.Entities.GrandeTache;
import com.ProjectManagement.digitalis.Exception.GtError;

import java.util.List;

public interface GtServices {

    GrandeTache saveGt(GrandeTache gt) throws GtError;

    GrandeTache editGt(Long idGt, GrandeTache gt)throws GtError;

    GrandeTache getGt(Long idGt)throws GtError;

    List<GrandeTache> listGt()throws GtError;

    void deleteGt(Long idGt)throws GtError;
}
