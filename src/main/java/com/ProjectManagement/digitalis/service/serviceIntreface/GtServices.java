package com.ProjectManagement.digitalis.service.serviceIntreface;

import com.ProjectManagement.digitalis.dto.GrandeTacheRequest;
import com.ProjectManagement.digitalis.entitie.GrandeTache;
import com.ProjectManagement.digitalis.entitie.SousTache;
import com.ProjectManagement.digitalis.exception.GtError;
import com.ProjectManagement.digitalis.exception.ProjetError;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GtServices {

    GrandeTache saveGt(GrandeTache gt) throws GtError;

    GrandeTache editGt(Long idGt, GrandeTacheRequest gt) throws GtError, ProjetError;


    GrandeTache getGt(Long idGt)throws GtError;

    List<GrandeTache> listGt()throws GtError;

    void deleteGt(Long idGt)throws GtError;


    List<SousTache> getstByGt(Long gtId);

    void updateGrandeTacheDates(GrandeTache grandeTache);



    Map<String,Long> countGtByStatus(Date startDate, Date endDate);
}
