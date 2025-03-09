package com.ProjectManagement.digitalis.service.serviceIntreface;

import com.ProjectManagement.digitalis.dto.SousTacheRequest;
import com.ProjectManagement.digitalis.dto.UpdateSurchargeAndObservation;
import com.ProjectManagement.digitalis.entitie.SousTache;
import com.ProjectManagement.digitalis.dto.StUpdateRequest;
import com.ProjectManagement.digitalis.exception.ProjetError;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface StServices {





    SousTache saveSousTache(SousTacheRequest sousTacheRequest, MultipartFile fichier, String fileName) throws IOException;

    Optional<SousTache> getSt(Long idSt);



    SousTache editSt(Long idSt, StUpdateRequest st) throws ProjetError;

    List<SousTache> listSt();

    void deleteSt(Long idSt);



    SousTache updateSurchargeAndObservation(Long id, UpdateSurchargeAndObservation dto);
}
