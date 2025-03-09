package com.ProjectManagement.digitalis.dto;

import com.ProjectManagement.digitalis.entitie.Evolution;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class StUpdateRequest {



    private String tacheSt;
    private Float chargesSt;
    private Date dateDeDebutSt;
    private Date dateDeFinSt;
    private Long ponderation;
    private Date dateDeFinReelleSt;

    private String remarquesGt;
    private Long idGt;
    private Evolution evolution;
    private Long idUser;
}
